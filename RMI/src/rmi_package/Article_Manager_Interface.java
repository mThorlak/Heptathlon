package rmi_package;

import java.rmi.Remote;
import java.util.*;

public interface Article_Manager_Interface extends Remote {
    public List<Article> getArticle() throws Exception;
}
