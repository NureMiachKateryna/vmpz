# Generated by Django 5.2.1 on 2025-05-28 15:14

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('myapp', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='article',
            name='created_at',
            field=models.DateTimeField(auto_now_add=True, default='2024-05-28 18:14:00'),
            preserve_default=False,
        ),
    ]
