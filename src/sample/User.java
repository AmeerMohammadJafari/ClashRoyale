package sample;

import javafx.scene.image.ImageView;

import java.io.*;
import java.util.ArrayList;

public class User implements Serializable {


    @Serial
    private static final long serialVersionUID = 6529685098267757690L;
    private String username;
    private String password;
    private int numberOfCups;
    private String currentLeague;
    private int level;
    private ArrayList<Deck> decks;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        numberOfCups = 0;
        currentLeague = "Beginner";
        level = 1;
        decks = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getNumberOfCups() {
        return numberOfCups;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public int getLevel() {
        return level;
    }

    public String getCurrentLeague() {
        return currentLeague;
    }

    private static ArrayList<User> getUsers(){
        User u = null;
        ArrayList<User> users = new ArrayList<>();
        try (FileInputStream fIn = new FileInputStream("Info.bin");
             ObjectInputStream input = new ObjectInputStream(fIn)) {
            while (true) {
                u = (User) input.readObject();
                if (u == null)
                    break;
                users.add(u);
            }
        } catch (EOFException | FileNotFoundException ignored) {

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    private static void saveUsersList(ArrayList<User> users){
        try (FileOutputStream fOut = new FileOutputStream("Info.bin");
             ObjectOutputStream output = new ObjectOutputStream(fOut)) {

            for (User element : users) {
                output.writeObject(element);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveUser(User user){
        ArrayList<User> users = getUsers();
        users.add(user);
        saveUsersList(users);
    }

    public static void rewriteUser(User user){
        ArrayList<User> users = getUsers();
        int i = 0 ;
        for(User u : users){
            if(u.getUsername().equals(user.getUsername())){
                users.set(i,user);
            }
            i++;
        }
        saveUsersList(users);
    }
}
