# Generated by Django 3.0.5 on 2021-04-02 00:27

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('api', '0003_project_priority'),
    ]

    operations = [
        migrations.AddField(
            model_name='project',
            name='status',
            field=models.CharField(default='OnGoing', max_length=20),
        ),
    ]
