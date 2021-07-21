package sample;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
/**
 * This class is for User and all the information that a single user contains
 */
public class User implements Serializable {


    @Serial
    private static final long serialVersionUID = 6529685098267757690L;
    private String username;
    private String password;
    private int numberOfCups;
    private String currentLeague;
    private int level;
    private ArrayList<Deck> decks;
    // a linked hashmap for user's battle history, a String for opponent's username
    // you should always keep the last 3 matches
    private LinkedHashMap<Boolean,String> battleHistory;

    /**
     * constructor
     * @param username username
     * @param password password
     */
    public User(String username, String password){
        this.username = username;
        this.password = password;
        numberOfCups = 0;
        currentLeague = "Beginner";
        level = 1;
        decks = new ArrayList<>();
        battleHistory = new LinkedHashMap<>();
    }

    /**
     * getter method for username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * getter method for password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * getter method for number of cups
     * @return number of cups
     */
    public int getNumberOfCups() {
        return numberOfCups;
    }

    /**
     * getter method for Decks list
     * @return decks list
     */
    public ArrayList<Deck> getDecks() {
        return decks;
    }

    /**
     * getter method for level
     * @return level of the user
     */
    public int getLevel() {
        return level;
    }

    /**
     * getter method for current league
     * @return current league
     */
    public String getCurrentLeague() {
        return currentLeague;
    }

    /**
     * getter method for battle history
     * @return battle history
     */
    public LinkedHashMap<Boolean, String> getBattleHistory() {
        return battleHistory;
    }

    /**
     * get the saved users in the File (info.bin)
     * @return users list
     */
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

    /**
     * save a list of users in the file (info.bin), it does not append but overwrites
     * @param users
     */
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

    /**
     * save a user at the end of the file (info.bin)
     * @param user user which will be saved
     */
    public static void saveUser(User user){
        ArrayList<User> users = getUsers();
        users.add(user);
        saveUsersList(users);
    }

    /**
     * change a user's info which has been saved in the file before
     * @param user user
     */
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

    /**
     * checks if the entered username exists in the (info.bin) or not
     * @param username username
     * @return true if is repetitive, false if not
     */
    public static boolean repetitiveUsername(String username){
        User u = null;
        try (FileInputStream fIn = new FileInputStream("Info.bin");
             ObjectInputStream input = new ObjectInputStream(fIn)) {
            while (true) {
                u = (User) input.readObject();
                if (u == null)
                    return false;
                if (username.equals(u.getUsername())) {
                    return true;
                }
            }
        } catch (EOFException ignored) {

        } catch (FileNotFoundException | NullPointerException ignored) {

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

}
