# Generated by Django 3.0.5 on 2021-03-19 23:21

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('api', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='employee',
            name='projects',
            field=models.CharField(blank=True, max_length=50, null=True),
        ),
    ]
