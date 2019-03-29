# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from snippets.models import Snippet,recommend
from snippets.serializers import SnippetSerializer,RecommendataionSerializer
from django.conf import settings
from django.core.mail import EmailMessage
import os
from google_drive_downloader import GoogleDriveDownloader as gdd


def mail(email,y):
    subject='recommendation'
    from_email=settings.EMAIL_HOST_USER
    to_email=[email]
    print("inside mail")


    msg = EmailMessage(
        subject=subject,
        body="Recommendation report of the provided dataset is attached below. THIS IS AN AUTOMATED MESSAGE - PLEASE DO NOT REPLY DIRECTLY TO THIS EMAIL",
        from_email=from_email,
        to=to_email
    )
    msg.attach_file('data/{}-{}.pdf'.format(email,y))
    msg.attach_file('mlrecommendation.py')
    msg.send(fail_silently=False)
    """
    msg = MIMEMultipart()

    #msg['From'] = from_email
    #msg['To'] = str(email)

    msg['Subject'] = "ML Recommendation"

    body = "Recommendation report of the provided dataset is attached below."
    msg.attach(MIMEText(body, 'plain'))

    filename = "output.pdf"
    attachment = open("data/output.pdf", "rb").decode('utf-8')
    body = MIMEText(attachment.read())

    msg.attach(body)
    try:
        send_mail(
            subject,
            msg.as_string(),
            from_email,
            (email,)
        )
    except Exception as e:
        print(e)
    """
    print("Finished mailing")
    return



def downloadDataset(url,name):
    try:
        id = url.split("=")[-1]
        gdd.download_file_from_google_drive(file_id=id,
                                            dest_path='./data/{}.csv'.format(name))
        return True
    except Exception as e:
        return e


@api_view(['POST'])
def recommendation(request,format=None):
    print("Inside Recommendation",request.method , request.method == "POST")
    if request.method == "POST":
        print("Hello World")
        data=request.data
        name = str(data['name'])
        code = str(data['code'])
        emailid= data['emailid']
        url=data['url']
        temp=  downloadDataset(url,name)
        if temp!=True:
            return Response(temp, status=status.HTTP_400_BAD_REQUEST)
        f=open('testRecommendation.py','r')
        temp=f.readlines()

        f=open('mlrecommendation.py','w')
        for x in temp:
            f.write(x+'\n')
        f.write('\n'+code)

        f.close()

        os.system('python mlRecommendation.py')
        os.rename('data/output.pdf','data/{}-{}.pdf'.format(emailid,name))
        mail(emailid,name)

        return Response(data, status=status.HTTP_201_CREATED)
    return Response("Erorr", status=status.HTTP_400_BAD_REQUEST)

@api_view(['GET', 'POST'])
def snippet_list(request , format=None):
    print("inside list")
    if request.method == 'GET':
        snippets = Snippet.objects.all()
        serializer = SnippetSerializer(snippets, many=True)
        return Response(serializer.data)

    elif request.method == 'POST':
        #print(request.data)
        serializer = SnippetSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['GET', 'PUT', 'DELETE'])
def snippet_detail(request, pk , format=None):

    #print("inside detail")
    try:
        snippet = Snippet.objects.get(pk=pk)

    except Snippet.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    if request.method == 'GET':
        serializer = SnippetSerializer(snippet)
        #print(serializer.data['code'])
        return Response(serializer.data['code'])

    elif request.method == 'PUT':
        serializer = SnippetSerializer(snippet, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    elif request.method == 'DELETE':
        snippet.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)


def LanguageDetection(code):
    f=open('G:\\major\\Drioditor\\test/test.txt','w')
    f.write(str(code))
    f.close()
    command=os.path.join('G:\\major\\Drioditor','test/env3/Scripts/python -m guesslang -a -i G:\\major\\Drioditor\\test\\test.txt')
    print(command)
    resu=os.popen(command).read()
    return resu.split(" ")[-1].lower()


@api_view(['GET'])
def test(request):
    res=LanguageDetection("for i in range(5) : print('Hello world')")
    print(res)
    return Response("Success")


@api_view(['GET'])
def result(request, pk, format=None):
    print("Inside result")
    try:
        snippet = Snippet.objects.get(title=str(pk))
    except Snippet.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)
    if request.method == 'GET':
        serializer = SnippetSerializer(snippet)
        code=serializer.data['code']
        res=LanguageDetection(code)
        print(res)
        if res =='python':
            f=open('temp.py','w')
            f.write(str(code))
            f.close()
            res = os.popen("python temp.py").read()
        elif res == 'java':
            filename=(code.split("class")[-1]).split('{')[0]
            f=open('{}.java'.format(filename),'w')
            f.write(str(code))
            f.close()
            os.system('javac {}.java'.format(filename))
            res = os.popen("java {}".format(filename)).read()
        else:
            f=open('temp.c','w')
            f.write(str(code))
            f.close()
            os.system('gcc temp.c')
            res = os.popen("./a.out").read()


        print(res)
        return Response('{}'.format(res))



@api_view(['GET'])
def fetch(request):
    with open('data/mayankmahavar111@gmail.com-iris.pdf', 'rb') as report:
        return Response(
            report.read(),
            headers={'Content-Disposition': 'attachment; filename="file.pdf"'},
            content_type='application/pdf')