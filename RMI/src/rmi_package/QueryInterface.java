package rmi_package;

import java.rmi.Remote;
import java.util.*;

public interface QueryInterface extends Remote {
    public List<Article> getAllArticle() throws Exception;
}
