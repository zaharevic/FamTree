package ru.gb.fam_tree.model.family_tree;

import ru.gb.fam_tree.model.family_tree.all_tree_items.FamilyTreeItem;
import ru.gb.fam_tree.model.family_tree.all_tree_items.human.Human;
import ru.gb.fam_tree.model.family_tree.comparators.ComparatorByAge;
import ru.gb.fam_tree.model.family_tree.comparators.ComparatorByName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FamilyTree<T extends FamilyTreeItem<T>> implements Serializable,Iterable<T> {
    private long objectId;
    private List<T> objectList;

    public FamilyTree(List<T> objectList){
        this.objectList = objectList;
    }

    public FamilyTree(){
        this(new ArrayList<>());
    }

    public boolean add(T t){
        if(t == null){
            return false;
        }
        if(!objectList.contains(t)){
            objectList.add(t);
            t.setId(objectId++);

            addToParents(t);
            addToChidrens(t);

            return true;
        }
        return false;
    }

    private boolean addToParents(T t){
        for(T parent: t.getParents()){
            parent.addChild(t);
            return true;
        }
        return false;
    }

    private void addToChidrens(T t){
        for (final T child: t.getChildrens()){
            child.addParent(t);
        }
    }

    public boolean setWedding(T t1, T t2){
        if(t1.getSpouse() == null && t2.getSpouse() == null){
            t1.setSpouse(t2);
            t2.setSpouse(t1);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean setDivorce(Human human1, Human human2){
        if(human1.getSpouse() == human2){
            human1.setSpouse(null);
            human2.setSpouse(null);
            return true;
        }
        return false;
    }


    public boolean checkId(long id){
        if(id >= objectId || id < 0){
            return false;
        }
        for(T t: objectList){
            if(t.getId() == id){
                return true;
            }
        }
        return false;
    }

    public T getById(long id){
        for(T t: objectList){
            if(t.getId() == id){
                return t;
            }
        }
        return null;
    }

    public String getInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append("In familly tree: ");
        sb.append(objectList.size());
        sb.append(" objects: \n");
        for(T t: objectList){
            sb.append(t);
            sb.append("\n");
        }
        return sb.toString();
    }

    public String toString(){
        return getInfo();
    }



    public Iterator<T> iterator(){
        return new HumanIterator<>(objectList);
    }

    public void sortByName(){
        objectList.sort(new ComparatorByName<>());
    }

    public void sortByAge(){
        objectList.sort(new ComparatorByAge<>());
    }

    public long getObjectId() {
        return objectId;
    }

    public void setParent(T t, T father){
        t.addParent(father);
        father.addChild(t);
    }
}
