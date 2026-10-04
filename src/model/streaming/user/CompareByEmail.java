package model.streaming.user;
import java.util.Comparator;

public class CompareByEmail implements Comparator<User>{

	@Override
	public int compare(User o1, User o2) {
			return (o1.getEmail().compareTo(o2.getEmail()));
	}
	
}
