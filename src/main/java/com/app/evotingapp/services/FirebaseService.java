package com.app.evotingapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.app.evotingapp.Entities.candidate;
import com.app.evotingapp.Entities.user;
import com.app.evotingapp.Entities.vote;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

public class FirebaseService {
    Firestore db;

    public FirebaseService() {
        //super();
        db = FirestoreClient.getFirestore();
    }
    public String getDocumentData(String collection, String document) throws InterruptedException, ExecutionException {    
        DocumentReference docRef = db.collection(collection).document(document);
        DocumentSnapshot snapshot = docRef.get().get();
        if (snapshot.exists()) {
            return "Document data: " + snapshot.getData();
        } else {
            return "No such document!";
        }
    }
    public List<candidate> getCandidates() throws InterruptedException, ExecutionException {
        List<candidate> candidates = new ArrayList<>();
        CollectionReference candidatesRef = db.collection("candidates");
        List<QueryDocumentSnapshot> documents = candidatesRef.get().get().getDocuments();
        for (DocumentSnapshot document : documents) {
            if (document.exists()) {
                candidates.add(document.toObject(candidate.class));
            }
        }
        return candidates;
    }
    public boolean userExists(String id) throws InterruptedException, ExecutionException {
        CollectionReference usersRef = db.collection("users");
        Query query = usersRef.whereEqualTo("id", id);
        QuerySnapshot querySnapshot = query.get().get();
        return !querySnapshot.isEmpty();
    }
    public boolean userExistsUsingEmail(String email) throws InterruptedException, ExecutionException {
        CollectionReference usersRef = db.collection("users");
        Query query = usersRef.whereEqualTo("email", email);
        QuerySnapshot querySnapshot = query.get().get();
        return !querySnapshot.isEmpty();
    }
    public void insertUser(String name, String email, String id, String hash) {
        CollectionReference usersRef = db.collection("users");
        usersRef.add(new user(name, email, id, hash));
    }
    public user getUser(String email) throws InterruptedException, ExecutionException {
        CollectionReference usersRef = db.collection("users");
        Query query = usersRef.whereEqualTo("email", email);
        QuerySnapshot querySnapshot = query.get().get();

        if (!querySnapshot.isEmpty()) {
            return querySnapshot.toObjects(user.class).get(0);
        } else {
            return null;
        }
    }
    public candidate getCandidate(String name) throws InterruptedException, ExecutionException {
        CollectionReference candidateRef = db.collection("candidates");
        Query query = candidateRef.whereEqualTo("name", name);
        QuerySnapshot querySnapshot = query.get().get();
        if (!querySnapshot.isEmpty()) {
            return querySnapshot.toObjects(candidate.class).get(0);
        } else {
            return null;
        }
    }
    public void makeVote(vote vote) throws InterruptedException, ExecutionException {
        CollectionReference votesRef = db.collection("votes");
        votesRef.add(vote);
    }
    public boolean userVoted(user user) throws InterruptedException, ExecutionException {
        CollectionReference votesRef = db.collection("votes");
        Query query = votesRef.whereEqualTo("user", user);
        QuerySnapshot querySnapshot = query.get().get();
        return !querySnapshot.isEmpty();
    }
    public int getVotes(candidate candidate) throws InterruptedException, ExecutionException {
        CollectionReference votesRef = db.collection("votes");
        Query query = votesRef.whereEqualTo("candidate", candidate);
        QuerySnapshot querySnapshot = query.get().get();
        return querySnapshot.size();
    }
    public int getVoteCount() throws InterruptedException, ExecutionException {
        CollectionReference votesRef = db.collection("votes");
        QuerySnapshot querySnapshot = votesRef.get().get();
        return querySnapshot.size();
    }
}
