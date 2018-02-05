package ro.ase.eu.proiect.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import ro.ase.eu.proiect.util.Line;

public class FbController {
    private DatabaseReference database;
    private FirebaseDatabase fcontroller;
    private static FbController firebaseController;

    private boolean responseInsert;

    private FbController() {
        fcontroller = FirebaseDatabase.getInstance();
    }

    public static FbController getInstance() {

        synchronized (FbController.class) {
            if (firebaseController == null) {
                firebaseController = new FbController();
            }
        }

        return firebaseController;
    }

    public boolean addAllLines(List<Line> lines) {

        if (lines == null || lines.size() == 0)
            return false;

        for (Line line : lines) {
            addLine(line);
        }
        return true;
    }

    public boolean addLine(Line line) {

        responseInsert = false;
        if (line == null)
            return responseInsert;

        database = fcontroller.getReference(FbConstants.TABLE_NAME_LINES);
        //genereaza un id pentru inregistrarea ta.
        if (line.getGlobalId() == null || line.getGlobalId().trim().isEmpty()) {
            line.setGlobalId(database.push().getKey());
        }
        database.child(line.getGlobalId()).setValue(line);
        addChangeEventListenerForEachLine(line);

        return responseInsert;
    }

    private void addChangeEventListenerForEachLine(Line line) {

        //acest eveniment se declanseaza pentru orice modificare adusa fiecarui jucator din firebase
        database.child(line.getId().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Line aux = dataSnapshot.getValue(Line.class);
                if (aux != null) {
                    responseInsert = true;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void findAllLines(ValueEventListener eventListener) {
        if (eventListener != null)
            database = fcontroller.getReference(FbConstants.TABLE_NAME_LINES);
        database.addValueEventListener(eventListener);
    }

    public Query removePlayer(Line line){
        if(line == null || line.getGlobalId() == null || line.getGlobalId().trim().isEmpty()){
            return null;
        }
        database = fcontroller.getReference(FbConstants.TABLE_NAME_LINES);

        database.child(line.getGlobalId()).removeValue();

        return database.child(line.getGlobalId());
    }
}

