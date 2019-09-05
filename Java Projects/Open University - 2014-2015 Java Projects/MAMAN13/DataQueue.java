public class DataQueue{
    private DataNode _rear, _front;
    public DataQueue(){
        _rear = new DataNode(0,null);
        _front = _rear;
    }
    public boolean empty(){ return( _front ==  _rear);}
    public void enqueue(DataNode newItem){
        DataNode temp = new DataNode(0,null);
        _rear.setValue(newItem.getValue());
        _rear.setLast(temp);
        _rear = temp;
    }
    public DataNode retrieve(){
        if (empty())
        return null;
        else{
            DataNode temp = _front;
            _front = _front.getLast();
            return temp;
        }
    }
    public void remove() { _front = _rear; }
}