package net.jo.testnews;

import java.util.List;

public interface IView {

    void showDialog();
    void hideDialog();

    void showList(List<JsonBean.GetVectorBean.ItemsBeanX> data);

    void showToast(String msg);
}
