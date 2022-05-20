package net.jo.testnews;

import java.util.ArrayList;
import java.util.List;

public class Presenter {

    private IView view;
    private Model model;

    public Presenter(IView view) {
        this.view = view;
        model = new Model();
    }

    public void showLoading() {
        view.showDialog();
    }


    void getData() {

        model.getData(new ICallback<JsonBean>() {
            @Override
            public void onSuccess(JsonBean data) {
                view.hideDialog();

                List<JsonBean.GetVectorBean.ItemsBeanX> news = new ArrayList<>();
                List<JsonBean.GetVectorBean.ItemsBeanX> dataList = data.getGetVector().getItems();
                for (int i = 0; i < dataList.size(); i++) {
                    String type = dataList.get(i).getType();
//                    String next = dataList.get(i + 1).getType();
                    if ("divider".equals(type) ||
                            "news".equals(type)) {
                        news.add(dataList.get(i));
                    }
                }
                List<JsonBean.GetVectorBean.ItemsBeanX> myList = new ArrayList<>();
                for (int i = 0; i < news.size(); i++) {
                    String type = news.get(i).getType();
                    if (i != news.size() - 1) {
                        String next = news.get(i + 1).getType();
                        if (!(type.equals("divider") && "divider".equals(next))) {
                            myList.add(news.get(i));
                        }

                    } else {
                        if (!"divider".equals(type))
                            myList.add(news.get(i));
                    }
                }
                view.showList(myList);
            }

            @Override
            public void onError(String msg) {
                view.showToast(msg);
            }

            @Override
            public void onFailure(String msg) {
                view.showToast(msg);
            }
        });
    }

}
