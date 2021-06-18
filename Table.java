// interface Table is implemented by both AvTable  and PrevTable
public interface Table{
    public void acquireForks(int ID);
    public void releaseForks(int ID);
}