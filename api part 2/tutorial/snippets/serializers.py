from rest_framework import serializers
from snippets.models import Snippet,recommend

class SnippetSerializer(serializers.ModelSerializer):
    class Meta:
        model = Snippet
        fields = ('id', 'title', 'code')


class RecommendataionSerializer(serializers.ModelSerializer):
    class Meta:
        model= recommend
        fields =('id' ,'name','code','emailid','url')