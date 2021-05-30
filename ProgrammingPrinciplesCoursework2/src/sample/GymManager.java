package sample;

//Interface for the gym manager
public interface GymManager {
     void commonDetails();
     void addDefaultMember();
     void addStudentMember();
     void addOver60Member();
     void saveMember(String command);
     void deleteMember();
     void printMembers();
     void sortNames();
}
