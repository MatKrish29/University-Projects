package sample;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;

public class Mongo {
    //Connecting the database
    MongoClient mongoClient = new MongoClient();
    MongoDatabase database = mongoClient.getDatabase("javaAssignment");
    MongoCollection<Document> collection = database.getCollection("person");
    //Getting the number of documents in the database
    int count = (int) collection.count();

    //Method to insert data into the database
    public void dataInsertDefaultMember(int membershipNumber, String name, String startMembershipDate) {
        Document myDoc = new Document("Membership Number", membershipNumber)
                .append("Member Name", name)
                .append("Start Membership Date", startMembershipDate)
                .append("Member Type", "Default Member")
                .append("School Name", "-")
                .append("Age", "-");
        collection.insertOne(myDoc);
    }

    //Method to insert data into the database
    public void dataInsertStudentMember(int membershipNumber, String name, String startMembershipDate,
                                        String schoolName) {
        Document myDoc = new Document("Membership Number", membershipNumber)
                .append("Member Name", name)
                .append("Start Membership Date", startMembershipDate)
                .append("Member Type", "Student Member")
                .append("School Name", schoolName)
                .append("Age", "-");
        collection.insertOne(myDoc);
    }

    //Method to insert data into the database
    public void dataInsertOver60Member(int membershipNumber, String name, String startMembershipDate, int age) {
        Document myDoc = new Document("Membership Number", membershipNumber)
                .append("Member Name", name)
                .append("Start Membership Date", startMembershipDate)
                .append("Member Type", "Over60 Member")
                .append("School Name", "-")
                .append("Age", age);
        collection.insertOne(myDoc);
    }

    //Method to retrieve data from the database
    public Object dataRetrieve(Object obj) {
        //Getting all the data under each column and storing them on an array list
        Iterator<Document> iterData = collection.find().iterator();
        ArrayList<Object> data = new ArrayList<Object>();
        while(iterData.hasNext()){
           data.add(iterData.next().get(obj));
        }
        return(data);
    }

    //Method to delete data from the database
    public void dataDelete(Object obj) {
        BasicDBObject query = new BasicDBObject();
        query.put("Membership Number", obj);
        collection.deleteOne(query);
    }
}