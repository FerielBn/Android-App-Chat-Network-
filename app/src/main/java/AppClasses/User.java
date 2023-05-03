package AppClasses;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.miniprojet.MainActivity;
import com.example.miniprojet.SignupActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class User {
    public String num_tel="";
    public String userName ="";
    public String email="";
    public String password="";
    public String user_img="https://res.cloudinary.com/hatem/image/upload/v1641410201/avatars/pjyi7s4od1pqhl1sibyn.png";
    public Boolean connected=false;

    // ****************************** THIS IS CONSTRUCTORS ****************************** //
    // this is for creating a user by register or login
    public User(String num_tel, String userName , String email, String password, Context context) {
        this.num_tel = num_tel;
        this.userName  = userName ;
        this.email = email;
        this.password = password;
        this.connected=true;
        this.UpdateStorage(context);
    }

    public User(String num_tel, String userName , String email, String password) {
        this.num_tel = num_tel;
        this.userName  = userName ;
        this.email = email;
        this.password = password;
        this.connected=false;
    }



    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.connected=false;
    }

    public User(String userName, String email, String num_tel) {
        this.userName = userName;
        this.email = email;
        this.num_tel = num_tel;
        this.connected=false;
    }

    public User() {
    }

    // this is for getting a user in application after login and register / like in normal page
    public User(Context context) {
        this.GetFromStorage(context);
    }
    // ****************************** THIS IS CONSTRUCTORS ****************************** //

    // ****************************** THIS IS STORAGE ****************************** //
    public void UpdateStorage(Context context){
        // here we add data to local storage
        SharedPreferences shared = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("num_tel", this.num_tel);
        editor.putString("userName ", this.userName );
        editor.putString("email", this.email);
        editor.putString("password", this.password);
        editor.putString("user_img", this.user_img);
        editor.putBoolean("connected", true);
        editor.apply();
        // everytime we set the storage we confirm that the user is really connected
        this.connected=true;
    }

    public void GetFromStorage(Context context){
        // here we will get the data from storage and set it in user
        SharedPreferences shared = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        this.num_tel =  shared.getString("num_tel", "");
        this.userName  = shared.getString("userName ", "");
        this.email = shared.getString("email", "");
        this.password = shared.getString("password", "");
        this.user_img = shared.getString("user_img", "https://res.cloudinary.com/hatem/image/upload/v1641410201/avatars/pjyi7s4od1pqhl1sibyn.png");
        this.connected = shared.getBoolean("connected", false);
    }

    public void UpdateUser(User user,Context context){
        this.userName = user.userName;
        this.password = user.password;
        this.num_tel = user.num_tel;
        this.user_img = user.user_img;
        this.email = user.email;
        this.connected = true;
        this.UpdateStorage(context);
    }
    // ****************************** THIS IS STORAGE ****************************** //

    // ****************************** THIS IS CONNECTION ****************************** //
    // this is for logout : i will remove user infos and set the connected to false
    public void UserLogout(Context context) {
        this.num_tel = "";
        this.userName  = "";
        this.email = "";
        this.password = "";
        this.user_img = "";
        this.connected=false;

        // here we add data to local storage
        SharedPreferences shared = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("num_tel", "");
        editor.putString("userName ", "");
        editor.putString("email", "");
        editor.putString("password", "");
        editor.putString("user_img", "");
        editor.putBoolean("connected", false);
        editor.apply();
    }


    // public void UserLogin(Context context,String email,String password,Runnable succ,Runnable fail){
    //     User user = this;
    //     Log.d("user", user.toString());
    //     FirebaseDatabase database = FirebaseDatabase.getInstance();
    //     DatabaseReference usersBD = database.getReference("users");
    //     // query to check if email already used
    //     Query checkMail = usersBD.orderByChild("email").equalTo(email);
    //     Log.d("email", email);
    //     checkMail.addListenerForSingleValueEvent(new ValueEventListener() {
    //         @Override
    //         public void onDataChange(@NonNull DataSnapshot snapshot) {
    //             Log.d("test", user.toString());
    //             if(snapshot.exists()){
    //                 Toast.makeText(context, "Email Exist", Toast.LENGTH_SHORT).show();
    //                 for (DataSnapshot snap_data : snapshot.getChildren()) {
    //                     User bd_user = snap_data.getValue(User.class);
    //                     if(bd_user.password.equals(password)){
    //                         Toast.makeText(context, "Welcome " + bd_user.userName, Toast.LENGTH_SHORT).show();
    //                         user.UpdateUser(bd_user,context);
    //                         Log.d("user_connect",bd_user.toString());
    //                         succ.run();
    //                     }else{
    //                         Toast.makeText(context, "Wrong Password ", Toast.LENGTH_SHORT).show();
    //                         fail.run();
    //                     }
    //                 }
    //             }
    //             else{
    //                 Toast.makeText(context, "No User With That Email", Toast.LENGTH_SHORT).show();
    //                 fail.run();
    //             }
    //         }
    //         @Override
    //         public void onCancelled(@NonNull DatabaseError error) {
    //             Log.d("user login error","error error");
    //             fail.run();
    //         }
    //     });
    // }

   public void UserLogin(Context context,String email,String password,Runnable succ,Runnable fail){
       User user = this;
       FirebaseDatabase database = FirebaseDatabase.getInstance();
       DatabaseReference usersBD_ref = database.getReference("users");
       usersBD_ref.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               Boolean exist=false;
               for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                   User bd_user = userSnapshot.getValue(User.class);
                   Log.d("user_from_bd",bd_user.toString());
                   if(bd_user.email.equals(email)){
                       if(bd_user.password.equals(password)){
                           Toast.makeText(context, "Welcome " + bd_user.userName, Toast.LENGTH_SHORT).show();
                           user.UpdateUser(bd_user,context);
                           Log.d("user_connect",user.toString());
                           exist=true;
                           succ.run();
                       }else{
                           Toast.makeText(context, "Wrong Password ", Toast.LENGTH_SHORT).show();
                           fail.run();
                       }
                   }
               }
               if(!exist){
                   Toast.makeText(context, "No User With That Email", Toast.LENGTH_SHORT).show();
                   fail.run();
               }
           }
           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               fail.run();
           }
       });
   }



    public void UserRegister(Context context,Runnable succ,Runnable fail){
        User user = this;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersBD = database.getReference("users");
        // query to check if email already used
        Query checkMail = usersBD.orderByChild("email").equalTo(this.email);
        Query checkPhone = usersBD.orderByChild("num_tel").equalTo(this.num_tel);

        checkMail.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Toast.makeText(context, "Email Already Exist", Toast.LENGTH_SHORT).show();
                    fail.run();
                }
                else{
                    checkPhone.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot2) {
                            if(snapshot2.exists()){
                                Toast.makeText(context, "Phone Already Exist", Toast.LENGTH_SHORT).show();
                                fail.run();
                            }
                            else{
                                usersBD.child(num_tel).setValue(user);
                                user.UpdateStorage(context);
                                Toast.makeText(context, "User Registered Successfuly", Toast.LENGTH_SHORT).show();
                                succ.run();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error2) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("user register error","error error");
                fail.run();
            }
        });
    }


    // ****************************** THIS IS CONNECTION ****************************** //


    @Override
    public String toString() {
        return "User{" +
                "num_tel='" + num_tel + '\'' +
                ", userName ='" + userName  + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", user_img='" + user_img + '\'' +
                ", connected=" + connected +
                '}';
    }

}


// list if images :
/*

  "https://res.cloudinary.com/hatem/image/upload/v1641410201/avatars/pjyi7s4od1pqhl1sibyn.png",
  "https://res.cloudinary.com/hatem/image/upload/v1641410201/avatars/fkanrzk1kin3y8bpig0u.png",
  "https://res.cloudinary.com/hatem/image/upload/v1641410204/avatars/dfwjmmym5bz5vefnd5oo.png",
  "https://res.cloudinary.com/hatem/image/upload/v1641410202/avatars/ym0cf8yyf5frfz1kgoou.png",
  "https://res.cloudinary.com/hatem/image/upload/v1641410204/avatars/sagacd08kd1ekatcxlz7.png",
  "https://res.cloudinary.com/hatem/image/upload/v1641410209/avatars/at5qscqfixgmvfvyyyc4.png",
  "https://res.cloudinary.com/hatem/image/upload/v1641410209/avatars/ebbie0jw8oc8brbrxx1c.png",
  "https://res.cloudinary.com/hatem/image/upload/v1641410209/avatars/fnmr3g3v3ulxm6gb3ovr.png",
  "https://res.cloudinary.com/hatem/image/upload/v1641410208/avatars/yr5qadehapvhhu5c8bb7.png",
  "https://res.cloudinary.com/hatem/image/upload/v1641410207/avatars/dla0dltwqsx6ho4f8csn.png",
  "https://res.cloudinary.com/hatem/image/upload/v1641410207/avatars/awjum6vrcknfepbnk2fn.png",
  "https://res.cloudinary.com/hatem/image/upload/v1641410206/avatars/q2hjex2i6hlu7gxtdacl.png",
  "https://res.cloudinary.com/hatem/image/upload/v1641410207/avatars/iazxvgllrwpdffdgvmra.png",
  "https://res.cloudinary.com/hatem/image/upload/v1641410204/avatars/djlhw6avzkzbyjrdaipg.png",
  "https://res.cloudinary.com/hatem/image/upload/v1641410204/avatars/d7j98fpyusbajzxici4t.png",
  "https://res.cloudinary.com/hatem/image/upload/v1641410201/avatars/zlsq5ahiinurwp7hc2nl.png",
  "https://res.cloudinary.com/hatem/image/upload/v1641410201/avatars/maquune1vsok39oc6mt9.png",
  "https://res.cloudinary.com/hatem/image/upload/v1641410200/avatars/wddztcg0knmsol3ukgdy.png",
  "https://res.cloudinary.com/hatem/image/upload/v1641410199/avatars/ubbi9p6v0bucxybm21sa.png",
  "https://res.cloudinary.com/hatem/image/upload/v1641410198/avatars/n4xj8meo7vlmyjtwaatm.png",
  "https://res.cloudinary.com/hatem/image/upload/v1641410197/avatars/ldsmgj80efvcmnp5z9ao.png",

*/
