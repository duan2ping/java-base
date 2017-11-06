/**
 * 每个具有迭代的对象都有一个内部类实现Iterator接口
 * 为了线程安全，在对集合操作时都会先检查集合是否被其他线程修改(add|remove)
 */
// #Iterator接口
public interface Iterator<E> {
   boolean hasNext();//判断是否存在下一个对象元素
   E next();         // 获得下一个元素
   void remove();   // 删除元素
}

// #ArrayList内部实现
private class Itr implements Iterator<E> {
    int cursor;       // 下一个元素的索引
    int lastRet = -1; // 上一个元素的索引，如果没有指向下一个元素，则默认-1（即没有调用next()）
    // 记录当前集合被修改的次数(add|remove)
    int expectedModCount = modCount;

    // 判断是否还有下一个元素
    public boolean hasNext() {
        return cursor != size;
    }

    // 获得下一个元素
    @SuppressWarnings("unchecked")
    public E next() {
        // 检查集合是否被其他线程修改
        checkForComodification();
        // 获得本次迭代的元素下标（如果连调多次next则会导致NoSuchElementException
        // 原因：当最后一次时next => cursor=size-1  再调用一次则 next => cursor=size）
        int i = cursor;
        if (i >= size)
            throw new NoSuchElementException();
        // 获得元素数组
        Object[] elementData = ArrayList.this.elementData;
        // 再次验证集合是否被修改
        if (i >= elementData.length)
            throw new ConcurrentModificationException();
        // 递增迭代下标
        cursor = i + 1;
        return (E) elementData[lastRet = i];
    }

    // 在迭代过程中删除元素
    // 如果没有next()就remove就会出现IllegalStateException异常
    // 原因lastRet = -1
    public void remove() {
        if (lastRet < 0)
            throw new IllegalStateException();
        // 检查是否被修改
        checkForComodification();

        try {
            // 在集合中删除该元素
            ArrayList.this.remove(lastRet);
            cursor = lastRet;
            lastRet = -1;
            // 更新修改次数
            expectedModCount = modCount;
        } catch (IndexOutOfBoundsException ex) {
            throw new ConcurrentModificationException();
        }
    }

    // 检查集合是否被修改(add|remove，防止出现线程不安操作)
    final void checkForComodification() {
        // 如果原始的修改次数和现在的修改次数不一样，则说明元素被其他线程修改
        // 则抛出异常
        if (modCount != expectedModCount)
            throw new ConcurrentModificationException();
    }
}