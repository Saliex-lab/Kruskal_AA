package Conteneurs;

public class Liste<E> {

    private class Node {
        E m_data;
        Node next;

        Node(E data) {
            m_data = data;
            next = null;
        }
    }

    private Node m_head;

    private int m_size = 0;

    public Liste() {
        m_head = null;
    }

    // Add data in list
    public void add(E data) {

        Node cell = new Node(data);

        if (m_head == null) {
            m_head = cell;
        } else {
            Node t_cell = m_head;

            while (t_cell.next != null) {
                t_cell = t_cell.next;
            }

            t_cell.next = cell;
        }
        m_size++;
    }

    // Get data
    public E get(int idx) throws Exception {

        if (idx >= 0 && idx < m_size) {

            int t_idx = 0;
            Node t_cell = m_head;

            while (t_idx != idx) {
                t_cell = t_cell.next;
                t_idx++;
            }
            return t_cell.m_data;
        } else {
            throw new Exception("Out of Range Get()");
        }
    }

    // Get size
    public int lenght() {
        return m_size;
    }

    //Print List
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m_size; i++) {
            sb.append('[');
            try {
                sb.append(this.get(i).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            sb.append(']');
        }
        return sb.toString();
    }
}
