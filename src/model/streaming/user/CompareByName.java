package model.streaming.user;
import java.util.Comparator;

public class CompareByName implements Comparator<User>{

	@Override
	public int compare(User o1, User o2) {
			return (o1.getName().compareTo(o2.getName()));
	}

}
