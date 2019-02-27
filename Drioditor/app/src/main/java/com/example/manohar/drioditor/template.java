package com.example.manohar.drioditor;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

public class template extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        int ml_algorithm=0;
        String url, dataset_name, report_gmail;

        Bundle values = getIntent().getExtras();
        if (values != null) {
            ml_algorithm=values.getInt("ml_algorithm");
            url = values.getString("url");
            dataset_name=values.getString("dataset_name");
            report_gmail=values.getString("report_gmail");
            //Log.i("values",""+ml_algorithm+""+url+""+dataset_name+""+report_gmail);
        }

        TextView out=findViewById(R.id.input_ml_code);
        switch (ml_algorithm){
            case 1:
                out.setText("def naive(dataset):\n" +
                        "    f=open('{}'.format(dataset),'r')\n" +
                        "    data=f.readlines()[1:]\n" +
                        "    temp=[]\n" +
                        "\n" +
                        "    for i in range(len(data)):\n" +
                        "        temp.append(data[i].split(',')[:-1])\n" +
                        "\n" +
                        "    X=np.array(temp)\n" +
                        "    X = X.astype(np.float)\n" +
                        "    Y=[]\n" +
                        "\n" +
                        "    for i in range(len(data)):\n" +
                        "        Y.append(data[i].split(',')[-1])\n" +
                        "\n" +
                        "    clf=naive_bayes.GaussianNB()\n" +
                        "    clf.fit(X,Y)\n" +
                        "    pred=clf.predict(X)\n" +
                        "    cm=confusion_matrix(Y,pred)\n" +
                        "\n" +
                        "    tp=cm[0][0]\n" +
                        "    fp=cm[0][1]\n" +
                        "    fn=cm[1][0]\n" +
                        "    tn=cm[1][1]\n" +
                        "\n" +
                        "    accuracy=round(float(tp+tn)/float(tp+tn+fp+fn),4)\n" +
                        "    tnr=round(float(tn)/float(tn+fp),4)\n" +
                        "    tpr=round(float(tp)/float(tp+fn),4)\n" +
                        "\n" +
<<<<<<< HEAD
                        "    return accuracy,tnr,tpr");
=======
                        "    return accuracy,tnr,tpr\n\n"+
                        "if __name__ == '__main__':\n"+
                        "    filename='data/iris.csv' \n"+
                        "    accuracy, tnr, tpr = SVM('{}'.format(filename))\n"+
                        "    print(accuracy, tnr , tpr)");
>>>>>>> 16d369319cddb60a4790ee6d73c871ba46c4dcfd
                break;
            case 2:
                out.setText("def knn(dataset):\n" +
                        "    f=open('{}'.format(dataset),'r')\n" +
                        "    data=f.readlines()[1:]\n" +
                        "    temp=[]\n" +
                        "\n" +
                        "    for i in range(len(data)):\n" +
                        "        temp.append(data[i].split(',')[:-1])\n" +
                        "\n" +
                        "    X=np.array(temp)\n" +
                        "    X = X.astype(np.float)\n" +
                        "    Y=[]\n" +
                        "\n" +
                        "    for i in range(len(data)):\n" +
                        "        Y.append(data[i].split(',')[-1])\n" +
                        "\n" +
                        "    #X_train,Y_train,X_test,Y_test=splitdataset(X,Y,0.70)\n" +
                        "\n" +
                        "\n" +
                        "    #print(Y[0])\n" +
                        "    #print(len(X_train),len(Y_train),len(X_test),len(Y_test))\n" +
                        "    #print(Y_train,Y_test)\n" +
                        "    clf=neighbors.KNeighborsClassifier()\n" +
                        "    clf.fit(X,Y)\n" +
                        "    pred=clf.predict(X)\n" +
                        "    #print(Y_test)\n" +
                        "    #print(pred)\n" +
                        "    cm=confusion_matrix(Y,pred)\n" +
                        "\n" +
                        "    print(cm)\n" +
                        "\n" +
                        "    tp=cm[0][0]\n" +
                        "    fp=cm[0][1]\n" +
                        "    fn=cm[1][0]\n" +
                        "    tn=cm[1][1]\n" +
                        "\n" +
                        "    accuracy=round(float(tp+tn)/float(tp+tn+fp+fn),4)\n" +
                        "    tnr=round(float(tn)/float(tn+fp),4)\n" +
                        "    tpr=round(float(tp)/float(tp+fn),4)\n" +
                        "\n" +
                        "    return accuracy,tnr,tpr\n\n"+
                        "if __name__ == '__main__':\n"+
                        "    filename='data/iris.csv' \n"+
                        "    accuracy, tnr, tpr = SVM('{}'.format(filename))\n"+
                        "    print(accuracy, tnr , tpr)");
                break;
            case 3:
                out.setText("def SVM(dataset):\n" +
                        "    f=open('{}'.format(dataset),'r')\n" +
                        "    data=f.readlines()[1:]\n" +
                        "    temp=[]\n" +
                        "\n" +
                        "    for i in range(len(data)):\n" +
                        "        temp.append(data[i].split(',')[:-1])\n" +
                        "\n" +
                        "    X=np.array(temp)\n" +
                        "    X = X.astype(np.float)\n" +
                        "    Y=[]\n" +
                        "\n" +
                        "    for i in range(len(data)):\n" +
                        "        Y.append(data[i].split(',')[-1])\n" +
                        "\n" +
                        "    clf=svm.LinearSVC()\n" +
                        "    clf.fit(X,Y)\n" +
                        "    pred=clf.predict(X)\n" +
                        "    cm=confusion_matrix(Y,pred)\n" +
                        "\n" +
                        "    tp=cm[0][0]\n" +
                        "    fp=cm[0][1]\n" +
                        "    fn=cm[1][0]\n" +
                        "    tn=cm[1][1]\n" +
                        "\n" +
                        "    accuracy=round(float(tp+tn)/float(tp+tn+fp+fn),4)\n" +
                        "    tnr=round(float(tn)/float(tn+fp),4)\n" +
                        "    tpr=round(float(tp)/float(tp+fn),4)\n" +
                        "\n" +
                        "    return accuracy,tnr,tpr\n\n"+
                        "if __name__ == '__main__':\n"+
                        "    filename='data/iris.csv' \n"+
                        "    accuracy, tnr, tpr = SVM('{}'.format(filename))\n"+
                        "    print(accuracy, tnr , tpr)");
                break;
            case 4:
                out.setText("def nn(dataset):\n" +
                        "    f=open('{}'.format(dataset),'r')\n" +
                        "    data=f.readlines()[1:]\n" +
                        "    temp=[]\n" +
                        "\n" +
                        "    for i in range(len(data)):\n" +
                        "        temp.append(data[i].split(',')[:-1])\n" +
                        "\n" +
                        "    X=np.array(temp)\n" +
                        "    X = X.astype(np.float)\n" +
                        "    Y=[]\n" +
                        "\n" +
                        "    for i in range(len(data)):\n" +
                        "        Y.append(data[i].split(',')[-1])\n" +
                        "\n" +
                        "    clf=neural_network.MLPClassifier()\n" +
                        "    clf.fit(X,Y)\n" +
                        "    pred=clf.predict(X)\n" +
                        "    cm=confusion_matrix(Y,pred)\n" +
                        "\n" +
                        "    tp=cm[0][0]\n" +
                        "    fp=cm[0][1]\n" +
                        "    fn=cm[1][0]\n" +
                        "    tn=cm[1][1]\n" +
                        "\n" +
                        "    accuracy=round(float(tp+tn)/float(tp+tn+fp+fn),4)\n" +
                        "    tnr=round(float(tn)/float(tn+fp),4)\n" +
                        "    tpr=round(float(tp)/float(tp+fn),4)\n" +
                        "\n" +
                        "    return accuracy,tnr,tpr\n\n"+
                        "if __name__ == '__main__':\n"+
                        "    filename='data/iris.csv' \n"+
                        "    accuracy, tnr, tpr = SVM('{}'.format(filename))\n"+
                        "    print(accuracy, tnr , tpr)");
                break;
            case 5:
                out.setText("Under Construction");
                break;
            case 6:
                out.setText("Under Construction");
                break;
            case 7:
                out.setText("Under Construction");
                break;
            case 8:
                out.setText("Under Construction");
                break;
            default:
                out.setText("Please select one of the given ML Algorithm");
                break;
        }

    }

}
