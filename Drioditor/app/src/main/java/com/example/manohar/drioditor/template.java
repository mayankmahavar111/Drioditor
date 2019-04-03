package com.example.manohar.drioditor;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import net.cryptobrewery.syntaxview.SyntaxView;

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

        SyntaxView out=findViewById(R.id.input_ml_code);
        switch (ml_algorithm){
            case 1:
                out.getCode().setText("from google_drive_downloader import GoogleDriveDownloader as gdd\n" +
                        "def downloadDataset(url,name):\n"+
                        "\ttry:\n"+
                        "\t\tid = url.split('=')[-1]\n"+
                        "\t\tgdd.download_file_from_google_drive(file_id=id,dest_path='./data/{}.csv'.format(name))\n"+
                        "\t\treturn True\n"+
                        "\texcept Exception as e:\n"+
                        "\t\treturn e\n"+
                        "def naive(dataset):\n" +
                        "\ttemp=  downloadDataset(url,name)\n"+
                        "\tif temp!=True:\n"+
                        "\t\treturn \n"+
                        "\tf=open('{}'.format(dataset),'r')\n" +
                        "\tdata=f.readlines()[1:]\n" +
                        "\ttemp=[]\n" +
                        "\n" +
                        "\tfor i in range(len(data)):\n" +
                        "\t\ttemp.append(data[i].split(',')[:-1])\n" +
                        "\n" +
                        "\tX=np.array(temp)\n" +
                        "\tX = X.astype(np.float)\n" +
                        "\tY=[]\n" +
                        "\n" +
                        "\tfor i in range(len(data)):\n" +
                        "\t\tY.append(data[i].split(',')[-1])\n" +
                        "\n" +
                        "\tclf=naive_bayes.GaussianNB()\n" +
                        "\tclf.fit(X,Y)\n" +
                        "\tpred=clf.predict(X)\n" +
                        "\tcm=confusion_matrix(Y,pred)\n" +
                        "\n" +
                        "\ttp=cm[0][0]\n" +
                        "\tfp=cm[0][1]\n" +
                        "\tfn=cm[1][0]\n" +
                        "\ttn=cm[1][1]\n" +
                        "\n" +
                        "\taccuracy=round(float(tp+tn)/float(tp+tn+fp+fn),4)\n" +
                        "\ttnr=round(float(tn)/float(tn+fp),4)\n" +
                        "\ttpr=round(float(tp)/float(tp+fn),4)\n" +
                        "\n" +
                        "\treturn accuracy,tnr,tpr\n" +
                        "if __name__ == '__main__':\n"+
                        "\tfilename='data/iris.csv' \n"+
                        "\taccuracy, tnr, tpr = naive('{}'.format(filename))\n"+
                        "\tprint(accuracy, tnr , tpr)\n");
                break;
            case 2:
                out.getCode().setText("from google_drive_downloader import GoogleDriveDownloader as gdd\n" +
                        "def downloadDataset(url,name):\n"+
                        "\ttry:\n"+
                        "\t\tid = url.split('=')[-1]\n"+
                        "\t\tgdd.download_file_from_google_drive(file_id=id,dest_path='./data/{}.csv'.format(name))\n"+
                        "\t\treturn True\n"+
                        "\texcept Exception as e:\n"+
                        "\t\treturn e\n"+
                        "def knn(dataset):\n" +
                        "\ttemp=  downloadDataset(url,name)\n"+
                        "\tif temp!=True:\n"+
                        "\t\treturn \n"+
                        "\tf=open('{}'.format(dataset),'r')\n" +
                        "\tdata=f.readlines()[1:]\n" +
                        "\ttemp=[]\n" +
                        "\n" +
                        "\tfor i in range(len(data)):\n" +
                        "\t\ttemp.append(data[i].split(',')[:-1])\n" +
                        "\n" +
                        "\tX=np.array(temp)\n" +
                        "\tX = X.astype(np.float)\n" +
                        "\tY=[]\n" +
                        "\n" +
                        "\tfor i in range(len(data)):\n" +
                        "\t\tY.append(data[i].split(',')[-1])\n" +
                        "\n" +
                        "\tclf=neighbors.KNeighborsClassifier()\n" +
                        "\tclf.fit(X,Y)\n" +
                        "\tpred=clf.predict(X)\n" +
                        "\tcm=confusion_matrix(Y,pred)\n" +
                        "\n" +
                        "\t#print(cm)\n" +
                        "\n" +
                        "\ttp=cm[0][0]\n" +
                        "\tfp=cm[0][1]\n" +
                        "\tfn=cm[1][0]\n" +
                        "\ttn=cm[1][1]\n" +
                        "\n" +
                        "\taccuracy=round(float(tp+tn)/float(tp+tn+fp+fn),4)\n" +
                        "\ttnr=round(float(tn)/float(tn+fp),4)\n" +
                        "\ttpr=round(float(tp)/float(tp+fn),4)\n" +
                        "\n" +
                        "\treturn accuracy,tnr,tpr\n" +
                        "if __name__ == '__main__':\n"+
                        "\tfilename='data/iris.csv' \n"+
                        "\taccuracy, tnr, tpr = knn('{}'.format(filename))\n"+
                        "\tprint(accuracy, tnr , tpr)\n");
                break;
            case 3:
                out.getCode().setText("from google_drive_downloader import GoogleDriveDownloader as gdd\n" +
                        "def downloadDataset(url,name):\n"+
                        "\ttry:\n"+
                        "\t\tid = url.split('=')[-1]\n"+
                        "\t\tgdd.download_file_from_google_drive(file_id=id,dest_path='./data/{}.csv'.format(name))\n"+
                        "\t\treturn True\n"+
                        "\texcept Exception as e:\n"+
                        "\t\treturn e\n"+
                        "def SVM(dataset):\n" +
                        "\ttemp=  downloadDataset(url,name)\n"+
                        "\tif temp!=True:\n"+
                        "\t\treturn \n"+
                        "\tf=open('{}'.format(dataset),'r')\n" +
                        "\tdata=f.readlines()[1:]\n" +
                        "\ttemp=[]\n" +
                        "\n" +
                        "\tfor i in range(len(data)):\n" +
                        "\t\ttemp.append(data[i].split(',')[:-1])\n" +
                        "\n" +
                        "\tX=np.array(temp)\n" +
                        "\tX = X.astype(np.float)\n" +
                        "\tY=[]\n" +
                        "\n" +
                        "\tfor i in range(len(data)):\n" +
                        "\t\tY.append(data[i].split(',')[-1])\n" +
                        "\n" +
                        "\tclf=svm.LinearSVC()\n" +
                        "\tclf.fit(X,Y)\n" +
                        "\tpred=clf.predict(X)\n" +
                        "\tcm=confusion_matrix(Y,pred)\n" +
                        "\n" +
                        "\ttp=cm[0][0]\n" +
                        "\tfp=cm[0][1]\n" +
                        "\tfn=cm[1][0]\n" +
                        "\ttn=cm[1][1]\n" +
                        "\n" +
                        "\taccuracy=round(float(tp+tn)/float(tp+tn+fp+fn),4)\n" +
                        "\ttnr=round(float(tn)/float(tn+fp),4)\n" +
                        "\ttpr=round(float(tp)/float(tp+fn),4)\n" +
                        "\n" +
                        "\treturn accuracy,tnr,tpr\n"+
                        "if __name__ == '__main__':\n"+
                        "\tfilename='data/iris.csv' \n"+
                        "\taccuracy, tnr, tpr = SVM('{}'.format(filename))\n"+
                        "\tprint(accuracy, tnr , tpr)\n");
                break;
            case 4:
                out.getCode().setText("from google_drive_downloader import GoogleDriveDownloader as gdd\n" +
                        "def downloadDataset(url,name):\n"+
                        "\ttry:\n"+
                        "\t\tid = url.split('=')[-1]\n"+
                        "\t\tgdd.download_file_from_google_drive(file_id=id,dest_path='./data/{}.csv'.format(name))\n"+
                        "\t\treturn True\n"+
                        "\texcept Exception as e:\n"+
                        "\t\treturn e\n"+
                        "def nn(dataset):\n" +
                        "\ttemp=  downloadDataset(url,name)\n"+
                        "\tif temp!=True:\n"+
                        "\t\treturn \n"+
                        "\tf=open('{}'.format(dataset),'r')\n" +
                        "\tdata=f.readlines()[1:]\n" +
                        "\ttemp=[]\n" +
                        "\n" +
                        "\tfor i in range(len(data)):\n" +
                        "\t\ttemp.append(data[i].split(',')[:-1])\n" +
                        "\n" +
                        "\tX=np.array(temp)\n" +
                        "\tX = X.astype(np.float)\n" +
                        "\tY=[]\n" +
                        "\n" +
                        "\tfor i in range(len(data)):\n" +
                        "\t\tY.append(data[i].split(',')[-1])\n" +
                        "\n" +
                        "\tclf=neural_network.MLPClassifier()\n" +
                        "\tclf.fit(X,Y)\n" +
                        "\tpred=clf.predict(X)\n" +
                        "\tcm=confusion_matrix(Y,pred)\n" +
                        "\n" +
                        "\ttp=cm[0][0]\n" +
                        "\tfp=cm[0][1]\n" +
                        "\tfn=cm[1][0]\n" +
                        "\ttn=cm[1][1]\n" +
                        "\n" +
                        "\taccuracy=round(float(tp+tn)/float(tp+tn+fp+fn),4)\n" +
                        "\ttnr=round(float(tn)/float(tn+fp),4)\n" +
                        "\ttpr=round(float(tp)/float(tp+fn),4)\n" +
                        "\n" +
                        "\treturn accuracy,tnr,tpr\n" +
                        "if __name__ == '__main__':\n"+
                        "\tfilename='data/iris.csv' \n"+
                        "\taccuracy, tnr, tpr = nn('{}'.format(filename))\n"+
                        "\tprint(accuracy, tnr , tpr)\n");
                break;
            case 5:
                 out.getCode().setText("Under Construction");
                break;
            case 6:
                out.getCode().setText("Under Construction");
                break;
            case 7:
                out.getCode().setText("Under Construction");
                break;
            case 8:
                out.getCode().setText("Under Construction");
                break;
            default:
                out.getCode().setText("Please select one of the given ML Algorithm");
                break;
        }

    }

}
