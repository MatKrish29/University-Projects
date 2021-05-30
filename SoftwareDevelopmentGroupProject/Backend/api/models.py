from django.db import models
from django.contrib.auth.models import PermissionsMixin
from django.contrib.auth.base_user import BaseUserManager, AbstractBaseUser
from django.utils import timezone
from rest_framework.authtoken.models import Token

class UserManager(BaseUserManager):

    def _create_user(self, username, email, password, is_staff, is_superuser, **extra_fields):
        now = timezone.now()
        if not username:
            raise ValueError('The given username must be set')
        if not email:
            raise ValueError('The given email must be set')
        if not password:
            raise ValueError('The given password must be set')

        email = self.normalize_email(email)
        user = self.model(username=username, email=email,
                          is_staff=is_staff, is_active=True,
                          is_superuser=is_superuser, last_login=now,
                          date_joined=now, **extra_fields)
        user.set_password(password)
        user.save(using=self._db)
        Token.objects.create(user=user)
        return user

    def create_user(self, username, email=None, password=None, **extra_fields):
        return self._create_user(username, email, password, False, False, ' ',
                                 **extra_fields)

    def create_superuser(self, username, email, password, **extra_fields):
        user = self._create_user(username, email, password, True, True,
                                 **extra_fields)
        user.is_active = True
        user.save(using=self._db)
        return user


class User(AbstractBaseUser, PermissionsMixin):
    username = models.CharField(max_length=30, unique=True)
    email = models.EmailField(max_length=250, unique=True)
    first_name = models.CharField(max_length=30, blank=True, null=True)
    last_name = models.CharField(max_length=30, blank=True, null=True)
    is_active = models.BooleanField(default=True)
    is_staff = models.BooleanField(default=False)
    is_superuser = models.BooleanField(default=False)
    date_joined = models.DateTimeField(default=timezone.now)
    birth_date = models.DateTimeField(blank=True, null=True)
    designation = models.CharField(max_length=30, blank=True, null=True)
    department = models.CharField(max_length=30, blank=True, null=True)

    objects = UserManager()

    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = ['username', ]


class Employee(models.Model):
    firstname = models.CharField(max_length=50, blank=False, null=False)
    lastname = models.CharField(max_length=50, blank=False, null=False)
    email = models.CharField(max_length=60, unique=True)
    experience = models.CharField(max_length=5, blank=False, null=False)
    designation = models.CharField(max_length=40, blank=False, null=False)
    department = models.CharField(max_length=40, blank=False, null=False)
    is_java = models.BooleanField(default=False)
    is_html = models.BooleanField(default=False)
    is_css = models.BooleanField(default=False)
    is_js = models.BooleanField(default=False)
    is_python = models.BooleanField(default=False)
    is_angular = models.BooleanField(default=False)
    is_csharp = models.BooleanField(default=False)
    is_react = models.BooleanField(default=False)
    is_php = models.BooleanField(default=False)
    is_cplus = models.BooleanField(default=False)
    is_mysql = models.BooleanField(default=False)
    is_nosql = models.BooleanField(default=False)
    is_bootstrap = models.BooleanField(default=False)
    is_ruby = models.BooleanField(default=False)
    is_oracle = models.BooleanField(default=False)
    is_creativity = models.BooleanField(default=False)
    is_leadership = models.BooleanField(default=False)
    is_communication = models.BooleanField(default=False)
    is_passion = models.BooleanField(default=False)
    is_teamwork = models.BooleanField(default=False)
    is_analytical = models.BooleanField(default=False)
    type = models.CharField(max_length=50, blank=True, null=True)
    grade = models.CharField(max_length=30, blank=True, null=True)
    specialization = models.CharField(max_length=50, blank=True, null=True)
    projects = models.CharField(max_length=50, blank=True, null=True)

    def __str__(self):
        return self.firstname + " " + self.lastname

    class Meta:
        db_table = "employees"

class Project(models.Model):
    client = models.CharField(max_length=50, blank=False, null=False)
    address = models.CharField(max_length=50, blank=False, null=False)
    email = models.CharField(max_length=60, unique=True)
    contactno = models.CharField(max_length=10, blank=False, null=False)
    solution_description = models.CharField(max_length=40, blank=False, null=False)
    date_admitted = models.DateTimeField(default=timezone.now)
    delivery_date = models.DateTimeField(blank=False, null=False)
    is_java = models.BooleanField(default=False)
    is_html = models.BooleanField(default=False)
    is_css = models.BooleanField(default=False)
    is_js = models.BooleanField(default=False)
    is_python = models.BooleanField(default=False)
    is_angular = models.BooleanField(default=False)
    is_csharp = models.BooleanField(default=False)
    is_react = models.BooleanField(default=False)
    is_php = models.BooleanField(default=False)
    is_cplus = models.BooleanField(default=False)
    is_mysql = models.BooleanField(default=False)
    is_nosql = models.BooleanField(default=False)
    is_bootstrap = models.BooleanField(default=False)
    is_ruby = models.BooleanField(default=False)
    is_oracle = models.BooleanField(default=False)
    repolink = models.CharField(max_length=150, blank=False, null=False)
    priority = models.CharField(max_length=10, blank=False, null=False, default='low')
    status = models.CharField(max_length=20, blank=False, null=False, default='OnGoing')

    def __str__(self):
        return self.client

    class Meta:
        db_table = "projects"
