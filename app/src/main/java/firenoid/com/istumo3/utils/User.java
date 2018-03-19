package firenoid.com.istumo3.utils;

/**
 * Created by OpiFrame on 07/12/2017.
 */
public class User {

private int id;
private String name;
private String email;
private String password;
  //  private String value;
   // private String date;
  //  private String time;
    private String gender ;
    private String type ;
    private String hei ;
    private String wei ;
    private String age ;
    private String picture;





  /*  public User(){

    }
    // constructor
    public User(int id, String name, String email,String password,String gender,String Type,String hei,String wei ,String age,String picture){

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.Type = Type;
        this.hei = hei;
        this.wei = wei;
        this.age = age;
        this.picture = picture;


    }*/




public int getId() {
return id;
}

public void setId(int id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}



   // public String getvalue() {
  //      return value;
  //  }
   /// public void setvalue(String value) {
   //     this.value = value;
   // }


   // public String getdate() {
    //    return date;
   // }
   // public void setdate(String date) {
   //     this.date = date;
   // }

   // public String gettime() {
   //     return time;
   // }
   // public void settime(String time) {
    //    this.time = time;
   // }









    public String getgender() {
        return gender;
    }

    public void setgender(String gender) {
        this.gender = gender;
    }

    public String gettype() {
        return type;
    }

    public void settype(String type) {
        this.type = type;
    }

    public String getage() {
        return age;
    }

    public void setage(String age) {
        this.age = age;
    }

    public String getpicture() {
        return picture;
    }

    public void setpicture(String picture) {
        this.picture = picture;
    }



    public String gethei() {
        return hei;
    }

    public void sethei(String hei) {
        this.hei = hei;
    }

    public String getwei() {
        return wei;
    }

    public void setwei(String wei) {
        this.wei = wei;
    }





}