package Conteneurs;

public class ListeTriable<E extends Comparable<E>> extends Liste<E> {

    // Sort the list

    public void sort() throws Exception {
        for (int i = 0; i < m_size - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < m_size; j++) {
                E dataJ = this.get(j);
                E dataMin = this.get(minIdx);
                
                if (dataJ.compareTo(dataMin) < 0) {
                    minIdx = j;
                }
            }
    
            if (minIdx != i) {
                E temp = this.get(i);
                set(i, this.get(minIdx));
                set(minIdx, temp);
            }
        }
    }
}

