package it.mytutor.business.impl.test;

import it.mytutor.domain.*;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestBusinness {

    /**
     * Metodi di test che generano i dati di Booking simulando findAllBooking
     * @return Lista di 50 prenotazioni
     */
    public static List<Booking> simulateFindAllBooking() {
        List<Booking> allPrenotazioni = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            allPrenotazioni.add(generaPrenotazione(i));
        }
        return allPrenotazioni;
    }

    /**
     * Genera una Prenotazione
     * @param i indice usato per generare la prenotazione
     * @return una prenotazione
     */
    private static Booking generaPrenotazione(int i) {
        Booking booking = new Booking();
        booking.setIdBooking(i);

        Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        booking.setDate(date);
        booking.setLessonState((1 + i) % 4);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        booking.setCreateDate(timestamp);
        booking.setUpdateDate(timestamp);
        try {
            booking.setPlanning(generaPlanning(1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        booking.setStudent();
        return booking;
    }


    /**
     * Metodi di test che generano i dati di Chat simulando simulateFindAllChatByUser
     * @return Lista di 20 chat
     */
    public static List<Chat> simulateFindAllChatByUser(){
        List<Chat> allChat = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            allChat.add(generateChat(i));
        }
        return allChat;
    }

    /**
     * Genera una Chat
     * @param i indice usato per generare la chat
     * @return una chat
     */
    public static Chat generateChat(int i){
        Chat chat = new Chat();
        chat.setIdChat(i);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        chat.setCreateDate(timestamp);
        chat.setUpdateDate(timestamp);
        return chat;
    }


    /**
     * Metodi di test che generano i dati di Lesson simulando simulateFindAllLesson
     * @return Lista di 20 lezioni
     */
    public static List<Lesson> simulateFindAllLesson() throws ParseException {
        List<Lesson> allLezioni = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            allLezioni.add(generaLezione(i));
        }
        return allLezioni;
    }


    private static Planning generaPlanning(int i) throws ParseException {
        Planning planning = new Planning();
        planning.setIdPlanning(i);
        SimpleDateFormat sdfP = new SimpleDateFormat("yyyy-MM-dd");
        String[] stringaDate = new String[]{"2019-06-18", "2019-05-22", "2019-06-13", "2019-06-16"};
//        planning.setDate(sdfP.parse(stringaDate[(i * 3) % 4]));
        String[] stringStartTime = new String[]{"8:00:00", "9:00:00", "10:00:00", "11:00:00", "12:00:00", "14:00:00", "15:00:00", "16:00:00", "17:00:00", "18:00:00"};
        Time startTimes = Time.valueOf(stringStartTime[(i * 3) % 10]);
        String[] stringEndTime = new String[]{"9:00:00", "10:00:00", "11:00:00", "12:00:00", "14:00:00", "15:00:00", "16:00:00", "17:00:00", "18:00:00", "19:00:00"};
        Time endTimes = Time.valueOf(stringEndTime[(i * 3) % 10]);
        planning.setStartTime(startTimes);
        planning.setEndTime(endTimes);
        planning.setLesson(generaLezione(i));
        return planning;
    }


    private static Teacher generaTeacher(int i) throws ParseException {
        Teacher teacher = new Teacher();
        teacher.setIdUser(i);
        teacher.setEmail("professore@gmail.it");
        teacher.setRoles(2);
        teacher.setPassword("123456789");
        teacher.setName("Giovanni");
        teacher.setSurname("Storti");
        teacher.setBirthday(Date.valueOf("1980-07-22"));
        teacher.setLanguage(true);
        teacher.setImage("qwertyuiopasdfghjklzxcvbnm");
        teacher.setIdTeacher(i);
        teacher.setPostCode(123);
        teacher.setCity("Pescasseroli");
        teacher.setRegion("Abruzzo");
        teacher.setStreet("via fasulla");
        teacher.setStreetNumber("1A");
        teacher.setByography("sono bravissimo");
        return teacher;
    }


    /**
     * Genera una Lesson
     * @param i indice usato per generare la Lesson
     * @return una Lesson
     */
    private static Lesson generaLezione(int i) throws ParseException {
        Lesson lesson = new Lesson();
        lesson.setIdLesson(i);
        String[] nomi = new String[]{"Matematica", "Fisica", "Informatica", "Network"};
        lesson.setName(nomi[(i * 3) % 4]);
        lesson.setPrice(10.00);
        lesson.setDescription("Bellissima Lezione");
        lesson.setPublicationDate(Date.valueOf("2019-04-22"));
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        lesson.setCreateDate(timestamp);
        lesson.setUpdateDate(timestamp);
        lesson.setSubject(generaSubject(1));
        lesson.setTeacher(generaTeacher(i));

        return lesson;
    }


    /**
     * Metodi di test che generano i dati di Message simulando simulateFindAllMessageByChat
     * @return Lista di 20 messaggi
     */
    public static List<Message> simulateFindAllMessageByUser(User user){
        List<Message> allMessageByChat = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            allMessageByChat.add(generateMessaggiByUser(i, user));
        }
        return allMessageByChat;
    }



    /**
     * Metodi di test che generano i dati di Message simulando simulateFindAllMessageByChat
     * @return Lista di 20 messaggi
     */
    public static List<Message> simulateFindAllMessageByChat(Chat chat) throws InterruptedException {
        List<Message> allMessageByChat = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            allMessageByChat.add(generateMessaggi(i, chat));
        }
        return allMessageByChat;
    }


    /**
     * Genera una Message
     * @param i indice usato per generare la Message
     * @return una Message
     */
    private static Message generateMessaggiByUser(int i, User user){
        Message message = new Message();
        message.setIdMessage(i);
        message.setText("prova Messaggio");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        message.setSendDate(timestamp);
        message.setCreateDate(timestamp);
        message.setUpdateDate(timestamp);
        // message.setIdChat(generateChat(i));
        List<User> userList = new ArrayList<>();

        userList.add(user);
        userList.add(generateUserMessage(1));
        // message.setIdUser(userList);
        return message;

    }


    /**
     * Genera una Message
     * @param i indice usato per generare la Message
     * @return una Message
     */
    private static Message generateMessaggi(int i, Chat chat) throws InterruptedException {
        Message message = new Message();
        message.setIdMessage(i);
        message.setText("prova Messaggio");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        message.setSendDate(timestamp);
        message.setCreateDate(timestamp);
        message.setUpdateDate(timestamp);
        //message.setIdChat(chat);
        List<User> userList = new ArrayList<>();

        userList.add(generateUser(i));
        userList.add(generateUser(i+1));
        //message.setIdUser(userList);
//        TimeUnit.MINUTES.sleep(1);
        return message;

    }

    public static User generateUser(int i){
        User user = new User();
        user.setIdUser(i);
        String[] emails = {"pippo@gmail.it", "antonio@gmail.it"};
        String[] names = {"pippo", "antonio"};
        String[] surnames = {"rossi", "verdi"};
        if (i % 2 == 0) {
            user.setName(names[0]);
            user.setSurname(surnames[0]);
            user.setEmail(emails[0]);
        }else {
            user.setName(names[1]);
            user.setSurname(surnames[1]);
            user.setEmail(emails[1]);
        }
        return user;
    }

    private static User generateUserMessage(int i){
        User user = new User();
        String names[] = {"mario", "pippo"};
        String surnames[] = {"rossi", "franco"};
        user.setIdUser(i);
        user.setName(names[i]);
        user.setSurname(surnames[i]);
        return user;
    }


    /**
     * Metodi di test che generano i dati di Subject simulando simulateFindAll
     * @return Lista di 20 materie
     */
    public static List<Subject> simulateFindAll(){
        List<Subject> allSubject = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            allSubject.add(generaSubject(i));
        }
        return allSubject;
    }

    /**
     * Genera una Subject
     * @param i indice usato per generare la Subject
     * @return una Subjecy
     */
    private static Subject generaSubject(int i){
        Subject subject = new Subject();
        subject.setIdSubject(i);
        String[] macroMateria = new String[]{"Analisi1", "Fisica1", "Compilatori", "Reti di Calcolatori"};
        String[] microMateria = new String[]{"Integrali", "Cinematica", "Parsing LR", "Subnet"};
        subject.setMacroSubject(macroMateria[(i * 3) % 4]);
        subject.setMicroSubject(microMateria[(i * 3) % 4]);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        subject.setCreateDate(timestamp);
        subject.setUpdateDate(timestamp);
        return subject;
    }


}
