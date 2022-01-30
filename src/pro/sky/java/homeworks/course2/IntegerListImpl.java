package pro.sky.java.homeworks.course2;

import java.util.Arrays;

public class IntegerListImpl implements IntegerList {
    private Integer[] integerList;
    private int size = 0;

    public IntegerListImpl(int length) {
        integerList = new Integer[length];
    }

    @Override
    public Integer add(Integer item) {
        if (item == null) {
            throw new NullPointerException("Добавление элемента null");
        }
        if (size == integerList.length - 1) {
            grow();
        }
        integerList[size] = item;
        size++;
        return item;
    }

    @Override
    public Integer add(int index, Integer item) {
        checkExceptionsForAddnSetMethods(index, item); //по принципу DRY немного сократил повторяющийся код с проверками на исключения
        if (size == integerList.length - 1)  {
            grow();
        }
            System.arraycopy(integerList, index, integerList, index + 1, integerList.length - (index + 1));
            integerList[index] = item;
            size++;
            return item;
    }

    private void grow() {
        integerList = Arrays.copyOf(integerList, (int) (integerList.length * 1.5));
    }

    private void resize() {
        integerList = Arrays.copyOf(integerList, (int) (integerList.length * 0.3));
    }

    @Override
    public Integer set(int index, Integer item) {
        checkExceptionsForAddnSetMethods(index, item);
        integerList[index] = item;
        size++;
        return item;
    }

    private void checkExceptionsForAddnSetMethods(int index, Integer item) { //в методе типичные повторяющиеся проверки
        if (item == null) {
            throw new NullPointerException("Добавление элемента null");
        }
        if (index >= integerList.length || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Ячейки " + index + " в массиве нет");
        }
        if (integerList[index - 1] == null) {
            throw new ArrayIndexOutOfBoundsException(index + " выходит за пределы фактического\n" +
                    "    // количества элементов");
        }
    }

    @Override
    public Integer removeItem(Integer item) {
        for (int j = 0; j < integerList.length; j++) {
            if (item.equals(integerList[j])) {
                integerList[j] = null;
                System.arraycopy(integerList, j + 1, integerList, j, integerList.length - 1 - j);
                size--;
                if (size < integerList.length / 2) {
                    resize();
                }
                return item;
            }
        }
        throw new NullPointerException(item + " не найдено");
    }

    @Override
    public Integer removeByIndex(int index) {
        if (index >= integerList.length || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Ячейки " + index + " в массиве нет");
        }
        if (integerList[index] == null) {
            throw new NullPointerException("Ячейка пуста");
        }
        Integer removedIntegerByIndex = integerList[index];
        integerList[index] = null;
        System.arraycopy(integerList, index + 1, integerList, index, integerList.length - 1 - index);
        size--;
        if (size < integerList.length / 2) {
            resize();
        }
        return removedIntegerByIndex;
    }

    @Override
    public int indexOf(Integer item) {
        for (int k = 0; k < integerList.length; k++) {
            if (integerList[k].equals(item)) {
                return k;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) {
        for (int k = integerList.length - 1; k >= 0; k--) {
            if (integerList[k] == null) {
                continue;
            }
            if (integerList[k].equals(item)) {
                return k;
            }
        }
        return -1;
    }

    @Override
    public Integer get(int index) {
        if (index >= integerList.length || index <= 0) {
            throw new ArrayIndexOutOfBoundsException("Ячейки " + index + " в массиве нет");
        }
        return integerList[index];
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(integerList);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        for (Integer s : integerList) {
            if (s != null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        Arrays.fill(integerList, null);
    }

    @Override
    public Integer[] toArray() {
        return Arrays.copyOf(integerList, integerList.length);
    }

    @Override
    public boolean equals(IntegerList otherList) {
        if (otherList == null) {
            throw new NullPointerException("Передано null");
        }
        IntegerListImpl that = (IntegerListImpl) otherList;
        return Arrays.equals(integerList, that.integerList);
    }

    @Override
    public boolean contains(Integer item) {
        quickSort(integerList, 0, size - 1);
        return searchBinary(integerList, item);
    }

    private Integer[] quickSort(Integer[] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
        return arr;
    }

    private int partition(Integer[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);
        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;
                swapElements(arr, i, j);
            }
        }
        swapElements(arr, i + 1, end);
        return i + 1;
    }

    private void swapElements(Integer[] arr, int left, int right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }

    private boolean searchBinary(Integer[] arr, int element) {
        int min = 0;
        int max = size - 1;
        while (min <= max) {
            int mid = (min + max) / 2;
            if (element == arr[mid]) {
                return true;
            }
            if (element < arr[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "IntegerListImpl{" +
                "integerList=" + Arrays.toString(integerList) +
                '}';
    }
}
