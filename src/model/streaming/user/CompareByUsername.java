package model.streaming.user;
import java.util.Comparator;

public class CompareByUsername implements Comparator<User>{

	@Override
	public int compare(User o1, User o2) {
			return (o1.getUsername().compareTo(o2.getUsername()));
	}

}
