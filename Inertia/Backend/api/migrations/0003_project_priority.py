# Generated by Django 3.0.5 on 2021-03-19 23:32

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('api', '0002_employee_projects'),
    ]

    operations = [
        migrations.AddField(
            model_name='project',
            name='priority',
            field=models.CharField(default='low', max_length=10),
        ),
    ]
