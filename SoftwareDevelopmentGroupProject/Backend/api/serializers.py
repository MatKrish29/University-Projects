from rest_framework import serializers
from allauth.account.adapter import get_adapter
from Backend import settings
from .models import User, Employee, Project
from allauth.account.utils import setup_user_email


class RegisterSerializer(serializers.Serializer):
    username = serializers.CharField(required=True, write_only=True)
    email = serializers.EmailField(required=settings.ACCOUNT_EMAIL_REQUIRED)
    first_name = serializers.CharField(required=True, write_only=True)
    last_name = serializers.CharField(required=True, write_only=True)
    designation = serializers.CharField(required=True, write_only=True)
    department = serializers.CharField(required=True, write_only=True)

    password1 = serializers.CharField(required=True, write_only=True)
    password2 = serializers.CharField(required=True, write_only=True)

    def validate_password1(self, password):
        return get_adapter().clean_password(password)

    def validate(self, data):
        if data['password1'] != data['password2']:
            raise serializers.ValidationError(
                "The two password fields didn't match.")
        return data

    def custom_signup(self, request, user):
        pass

    def get_cleaned_data(self):
        return {
            'first_name': self.validated_data.get('first_name', ''),
            'last_name': self.validated_data.get('last_name', ''),
            'designation': self.validated_data.get('designation', ''),
            'department': self.validated_data.get('address', ''),
            'user_type': self.validated_data.get('department', ''),
            'password1': self.validated_data.get('password1', ''),
            'email': self.validated_data.get('email', ''),
        }

    def save(self, request):
        adapter = get_adapter()
        user = adapter.new_user(request)
        self.cleaned_data = self.get_cleaned_data()
        adapter.save_user(request, user, self)
        self.custom_signup(request, user)
        setup_user_email(request, user, [])
        user.save()
        return user


class UserDetailsSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ('id', 'username', 'email', 'first_name',
                  'last_name', 'designation', 'department')


class EmployeeDetailsSerializer(serializers.ModelSerializer):
    class Meta:
        model = Employee
        fields = '__all__'


class ProjectDetailsSerializer(serializers.ModelSerializer):
    class Meta:
        model = Project
        fields = '__all__'

