public interface ChannelWorker<Task extends ChannelTask> extends Runnable {
    void setTask(Task task);
}
