# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models

# Create your models here.
class Snippet(models.Model):
    title = models.CharField(max_length=100, blank=True, default='')
    code = models.TextField()
    class Meta:
        ordering = ('title',)
        
        

class recommend(models.Model):
    name = models.CharField(max_length=100, blank=True, default='')
    code = models.TextField()
    emailid = models.CharField(max_length=100, blank=True, default='')
    url =models.CharField(max_length=100, blank=True, default='')
    class Meta:
        ordering = ('name',)
