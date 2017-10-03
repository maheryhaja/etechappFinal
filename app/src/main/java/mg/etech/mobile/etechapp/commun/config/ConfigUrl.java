package mg.etech.mobile.etechapp.commun.config;

/**
 * Created by mahery.haja on 31/08/2017.
 */
public class ConfigUrl {

    public static final String BASE_URL = "https://42answers.000webhostapp.com";
//    public static final String BASE_URL = "http://10.0.2.2:4000";

    public static class User {
        public static final String LOG_USER = "/user/login";
        public static final String CREATE_USER = "/user/create";

    }

    public static class Pole {
        public static final String FIND_ALL = "/pole/getAll";
    }

    public static class employe {
        public static final String FIND_ALL = "/employe/getAll";
        //        public static final String FIND_ALL = "/static/employe.json";
        public static final String CREATE_EMPLOYE = "/employe/create";
        public static final String UPDATE_EMPLOYE = "/employe/edit";
        public static final String DELETE_EMPLOYE = "/employe/delete";
        public static final String ADD_EMPLOYE_POSTE = "/employe/addPoste";
    }

    public static class poste {
        public static final String FIND_ALL = "/general/getConfig";
    }


}
