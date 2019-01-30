# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from snippets.models import Snippet
from snippets.serializers import SnippetSerializer
import os
from google_drive_downloader import GoogleDriveDownloader as gdd



def downloadDataset(url,name):
    try:
        id = url.split("=")[-1]
        gdd.download_file_from_google_drive(file_id=id,
                                            dest_path='./data/{}'.format(name))
        return True
    except Exception as e:
        print e
        return False


@api_view(['GET' , 'PUT'])
def recommendation(request,format=None):
    pass

@api_view(['GET', 'POST'])
def snippet_list(request , format=None):
    print("inside list")
    if request.method == 'GET':
        snippets = Snippet.objects.all()
        serializer = SnippetSerializer(snippets, many=True)
        return Response(serializer.data)

    elif request.method == 'POST':
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