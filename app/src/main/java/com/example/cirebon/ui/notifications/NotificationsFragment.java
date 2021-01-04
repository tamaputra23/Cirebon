package com.example.cirebon.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.cirebon.BaseActivity;
import com.example.cirebon.LocaleHelper;
import com.example.cirebon.R;
import com.example.cirebon.SplashScreen;
import com.example.cirebon.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class NotificationsFragment extends Fragment {
    TextView tv_nameprofile, tv_uname, tv_Phone, tv_Mail;
    DatabaseReference mDatabase;
    Button btn_signout;
    private static final String TAG = "ProfileFragment";
    private NotificationsViewModel notificationsViewModel;
    private RadioGroup radioBahasaGroup;
    private RadioButton indonesia, english;
    Button simpan;
    CardView logout;
    Context context;
    Resources resources;
    String bahasa;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        tv_nameprofile = root.findViewById(R.id.tv_Nameprofile);
        tv_Mail = root.findViewById(R.id.tv_Email);
        tv_Phone = root.findViewById(R.id.tv_Phone);
        logout = root.findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        radioBahasaGroup = root.findViewById(R.id.radioSex);
        simpan = root.findViewById(R.id.btn_simpan);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioBahasaGroup.getCheckedRadioButtonId();
                indonesia = root.findViewById(R.id.radioMale);
                english = root.findViewById(R.id.radioFemale);
                if (indonesia.isChecked()){
                    context = LocaleHelper.setLocale(getContext(), "en");
                    resources = context.getResources();
                    bahasa = "Bahasa Indonesia diplih";
                    String language = "in"; //For ENGLISH Language
//Change language for different Languages like: for Danish : “da”, Germany : “de”, for Norweign : “nb” etc
                    Locale locale = new Locale(language);
                    Locale.setDefault(locale);
                    Configuration config = getResources().getConfiguration();
                    config.locale = locale;
                    getResources().updateConfiguration(config,
                            getResources().getDisplayMetrics());
                    Toast.makeText(getContext(),
                            bahasa, Toast.LENGTH_SHORT).show();
                    getActivity().recreate();
                }
                else if (english.isChecked()){
                    context = LocaleHelper.setLocale(getContext(), "in");
                    resources = context.getResources();
                    bahasa = "English has Chosen";
                    String language = "en"; //For ENGLISH Language
//Change language for different Languages like: for Danish : “da”, Germany : “de”, for Norweign : “nb” etc
                    Locale locale = new Locale(language);
                    Locale.setDefault(locale);
                    Configuration config = getResources().getConfiguration();
                    config.locale = locale;
                    getResources().updateConfiguration(config,
                            getResources().getDisplayMetrics());
                    Toast.makeText(getContext(),
                            bahasa, Toast.LENGTH_SHORT).show();
                    getActivity().recreate();
                }

            }
        });
        return root;
    }
    @Override
    public void onStart() {
        super.onStart();
        final String userId = BaseActivity.getUid();
        mDatabase.child("users").child(userId).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);
                        String name = user.getFirstname();
                        String phone = user.getPhonenumber();
                        String email = user.getEmail();
                        tv_nameprofile.setText(name);
                        tv_Mail.setText(email);
                        tv_Phone.setText(phone);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
    }
    public void logout (){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getContext(), SplashScreen.class));
        getActivity().finish();
    }
}