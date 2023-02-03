package project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class ExperimentList {

    static String json = """
            {
                  "name":"Elon Musk",
                  "contact":{
                     "address":"NYC City",
                     "tel.:":"666"
                  },
                  "name":"Bill Gates",
                  "contact":{
                     "address":"California",
                     "tel.":"999"
                  }
            }""";
 /*
    // string output to Map Object
    static Gson gson = new Gson();
    static Type type = new TypeToken<LinkedHashMap<String, Object>>(){}.getType();
    static LinkedHashMap<String, Object> map = gson.fromJson(json, type);

    GsonBuilder gsonBuilder = new GsonBuilder();  // my json Builder
    Gson prettyGson = gsonBuilder.setPrettyPrinting().create(); //.setPrettyPrinting()
    String jsonOut = prettyGson.toJson(map);



    public  String getJSON () {
        return jsonOut;
    }
  */
    //System.out.println("Sent: \n" + jsonOut); // вывели красиво


}
