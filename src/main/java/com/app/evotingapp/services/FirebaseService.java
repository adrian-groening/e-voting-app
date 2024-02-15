package com.app.evotingapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.app.evotingapp.Entities.candidate;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.vaadin.flow.component.notification.Notification;

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

}
