package org.ccffee.utils.io;

import org.ccffee.utils.iteration.ArrayUtils;

public class MultipleSavable implements Savable {

    Savable[] savable;

    public MultipleSavable(Savable... savable){
        this.savable = savable;
    }

    public void add(Savable savable){
        this.savable = ArrayUtils.addAndExpand(this.savable,savable);
    }
    public void addArray(Savable... savable){
        this.savable = ArrayUtils.addArrayAndExpand(this.savable,savable);
    }

    @Override
    public void save() {
        for(Savable s : savable)s.save();
    }
}
