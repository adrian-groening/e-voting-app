package com.app.evotingapp.services;

import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
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

}
