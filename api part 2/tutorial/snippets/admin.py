# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.contrib import admin
from snippets.models import Snippet,recommend

admin.site.register(Snippet)
admin.site.register(recommend)

# Register your models here.
