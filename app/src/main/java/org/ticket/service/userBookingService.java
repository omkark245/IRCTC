package org.ticket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.internal.org.objectweb.asm.TypeReference;
import org.ticket.entities.user;
import org.ticket.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

//import static java.util.spi.ToolProvider.findFirst;
//import static jdk.internal.org.jline.utils.InfoCmp.Capability.user1;

public class userBookingService {
    private user User;
    private List<Object> userList;

    private ObjectMapper objectMap = new ObjectMapper();
    private  static  final String USERS_PATH="../localdb/user.json";
    public userBookingService( user User1) throws IOException
    {
        this.User=User1;
        File users= new File(USERS_PATH);
        userList=objectMap.readValue(users,new TypeReference<List<user>>(){});

    }
    public userBookingService( ) throws IOException
    {

        loadUser();

    }
    public List<user>loadUser()throws IOException
    {
        File users= new File(USERS_PATH);
        userList=objectMap.readValue(users,new TypeReference<List<user>>(){});
    }


    public Boolean loginUser()
    {
        //Predicate<? super user> User1;
        Optional<Object> foundUser=userList.stream().filter(User1->{
            return User1.getNames().equals(user.getNames()) && UserServiceUtil.checkPassword(user.getPassword(),User1.getHashPassword()
        }).findFirst();
            return foundUser.isPresent();

    }

    public boolean signup(user User1)
    {
        try
        {
            userList.add(User1);
            saveUserListToFile();
            return  Boolean.TRUE;

        }
        catch (IOException ex)
        {
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws IOException
    {
        File userFile=new File(USERS_PATH);
        objectMap.writeValue(userFile,userList);

    }

    public  void fetchBooking()
    {
        user.printTickets();
    }

    public Boolean cancelBooking(String ticketId)
    {
        return Boolean.FALSE;
    }
}
