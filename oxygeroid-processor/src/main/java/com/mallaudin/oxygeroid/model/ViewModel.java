package com.mallaudin.oxygeroid.model;

/**
 * <p>ViewModel is simple class which represents data parsed from a
 * single xml element and stores it in plain text.</p>
 *
 * @author M.Allaudin
 * @version 1.0
 *          <p>
 *          Created on 2016-12-25 12:06.
 */

public class ViewModel {

    /**
     * <p>Id represents the actual id value extracted from this xml element.
     * For example, for attribute android:id="@+id/sample_id"
     * Id = simple_id </p>
     */
    private String id;


    /**
     * <p>Type is the type of view represented by this xml element. e.g.
     * TextView, GridView etc </p>
     */
    private String type;

    /**
     * <p>Name represents the title case id. e.g.
     * for id "name_text_view", name is equal to "NameTextView" </p>
     */
    private String name;

    /**
     * <p>Package name stores the package name of the type and it defaults
     * to <code>android.widget</code>. e.g. for view "com.mallaudin.TextView",
     * package name equals to "com.mallaudin"</p>
     */
    private String packageName;


    public boolean isFragment() {
        return type.equals("fragment");
    } // isFragment

    public boolean isInclude() {
        return type.equals("include");
    }

    public boolean isMerge() {
        return type.equals("merge");
    }

    public boolean shouldInclude() {
        return !isFragment() && !isInclude() && !isMerge();
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
} // ViewModel
