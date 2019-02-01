# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from snippets.models import Snippet,recommend
from snippets.serializers import SnippetSerializer,RecommendataionSerializer
import os
from google_drive_downloader import GoogleDriveDownloader as gdd



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
    print("Inside Recommendation")
    if request.method == "POST":

        serializer =RecommendataionSerializer(data=request.data)
        if serializer.is_valid():
            data=request.data
            name = data['name']
            code = data['code']
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

            os.system('python mlRecommendation.py')

            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['GET', 'POST'])
def snippet_list(request , format=None):
    #print("inside list")
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
        f=open('temp.py','w')
        f.write(str(code))
        f.close()
        res=os.popen("python temp.py").read()
        print(res)
        return Response('{}'.format(res))