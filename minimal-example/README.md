

- https://github.com/cljfx/cljfx
- https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html


## tab change

```java
SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();

selectionModel.select(tab); //select by object
selectionModel.select(1); //select by index starting with 0
selectionModel.clearSelection(); //clear your selection
```