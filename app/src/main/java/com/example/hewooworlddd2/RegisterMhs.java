package com.example.hewooworlddd2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterMhs extends AppCompatActivity {
    //private final RegMahasiswa rmhs = new RegMahasiswa();
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_mhs);

        class RegMahasiswa extends Fragment {
            private final String TAG = RegisterMhs.class.getSimpleName();
            //FirebaseFirestore db = FirebaseFirestore.getInstance();
            private FirebaseFirestore firebaseFirestoreDb;
            private EditText noMhs;
            private EditText namaMhs;
            private EditText phoneMhs;
            private Button buttonSimpan;
            private Button buttonHapus;

            @Override
            public View onCreateView(LayoutInflater inflater,
                                     ViewGroup container,
                                     Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.reg_mhs, container, false);
                noMhs = view.findViewById(R.id.noMhs);
                namaMhs = view.findViewById(R.id.namaMhs);
                phoneMhs = view.findViewById(R.id.phoneMhs);
                buttonSimpan = view.findViewById(R.id.simpanButton);
                buttonHapus = view.findViewById(R.id.hapusButton);
                buttonSimpan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //sanity check
                        if (!noMhs.getText().toString().isEmpty() && !namaMhs.getText().toString().isEmpty()) {
                            tambahMahasiswa();
                        } else {
                            Toast.makeText(requireActivity(), "No dan Nama Mhs tidak boleh kosong",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                buttonHapus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDataMahasiswa();
                    }
                });

                return view;
            }

            private void tambahMahasiswa() {

                Mahasiswa mhs = new Mahasiswa(noMhs.getText().toString(),
                        namaMhs.getText().toString(),
                        phoneMhs.getText().toString());

                firebaseFirestoreDb.collection("DaftarMhs").document().set(mhs)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(requireActivity(), "Mahasiswa berhasil didaftarkan",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(requireActivity(), "ERROR" + e.toString(),
                                        Toast.LENGTH_SHORT).show();
                                Log.d("TAG", e.toString());
                            }
                        });



            /*
            private void addMhs(){
                Map<String, Object> user = new HashMap<>();
                user.put("first", "Ada");
                user.put("last", "Lovelace");
                user.put("born", 1815);

            // Add a new document with a generated ID
                db.collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
            }
            */
            }

            private void getDataMahasiswa() {
                DocumentReference docRef = firebaseFirestoreDb.collection("DaftarMhs").document("mhs1");
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Mahasiswa mhs = document.toObject(Mahasiswa.class);
                                noMhs.setText(mhs.getNim());
                                namaMhs.setText(mhs.getNama());
                                phoneMhs.setText(mhs.getPhone());
                            } else {
                                Toast.makeText(requireActivity(), "Document tidak ditemukan",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(requireActivity(), "Document error : " + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            private void deleteDataMahasiswa() {
                firebaseFirestoreDb.collection("DaftarMhs").document("mhs1")
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                noMhs.setText("");
                                namaMhs.setText("");
                                phoneMhs.setText("");
                                Toast.makeText(requireActivity(), "Mahasiswa berhasil dihapus",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(requireActivity(), "Error deleting document: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        }
    }
}
