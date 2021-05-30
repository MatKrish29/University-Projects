from django.http import Http404
import json
import ast

from rest_framework import viewsets, status
from rest_framework.authentication import TokenAuthentication
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.decorators import api_view
from django import forms

from api.models import User, Employee, Project
from api.serializers import UserDetailsSerializer, RegisterSerializer, EmployeeDetailsSerializer, \
    ProjectDetailsSerializer
from django.core.mail import send_mail
import pickle
import joblib
from numpy import array


class UserViewSet(viewsets.ModelViewSet):
    queryset = User.objects.all()
    serializer_class = UserDetailsSerializer


class UserDetail(APIView):
    authentication_classes = [TokenAuthentication, ]
    permission_classes = [IsAuthenticated, ]

    def get_object(self, id):
        try:
            return User.objects.get(id=id)
        except User.DoesNotExist:
            raise Http404

    def get(self, request, id, format=None):
        snippet = self.get_object(id=id)
        serializer = RegisterSerializer(snippet)
        return Response(serializer.data)

    def put(self, request, id, format=None):
        user = self.get_object(id=id)
        serializer = RegisterSerializer(user, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, id, format=None):
        user = self.get_object(id=id)
        user.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)

@api_view(['POST'])
def employee_create(request):
    employee = request.data

    developer_type_pkl = open('developer_type_classifier.pkl', 'rb')
    developer_specialization_pkl = open('developer_specialization_classifier.pkl', 'rb')

    dev_type_model = pickle.load(developer_type_pkl)
    dev_specialization_model = pickle.load(developer_specialization_pkl)

    # dev_cluster_model = joblib.load("clustered_model.sav")

    skills = [employee.get('is_html', False), employee.get('is_css', False), employee.get('is_js', False),
                employee.get('is_angular', False), employee.get('is_react', False),
                employee.get('is_bootstrap', False), employee.get('is_java', False), employee.get('is_python', False),
                employee.get('is_ruby', False), employee.get('is_csharp', False), employee.get('is_cplus', False),
                employee.get('is_php', False), employee.get('is_mysql', False), employee.get('is_nosql', False),
                employee.get('is_oracle', False), employee.get('is_leadership', False),
                employee.get('is_teamwork', False), employee.get('is_communication', False),
                employee.get('is_analytical', False), employee.get('is_creativity', False),
                employee.get('is_passion', False)]

    for i in range(0, len(skills)):
        if skills[i]:
            skills[i] = 1
        else:
            skills[i] = 0
        i += 1

    employee_skills_array = array([skills])

    dev_type = dev_type_model.predict(employee_skills_array)
    dev_specialization = dev_specialization_model.predict(employee_skills_array)

    if 'Frontend' in str(dev_type):
        developer_type = 'Frontend'
    elif 'Backend' in str(dev_type):
        developer_type = 'Backend'
    else:
        developer_type = 'Full-Stack'

    if 'Level-1' in str(dev_type):
        grade = 'Level-1'
    elif 'Level-2' in str(dev_type):
        grade = 'Level-2'
    else:
        grade = 'Level-3'

    if 'None' in str(dev_specialization):
        specialization = 'None'
    elif 'Frontend' in str(dev_specialization):
        specialization = 'Frontend'
    elif 'Backend' in str(dev_specialization):
        specialization = 'Backend'
    else:
        specialization = 'Full-Stack'

    serializer = EmployeeDetailsSerializer(data=employee)
    print(serializer)
    if serializer.is_valid():
        employee_instance = serializer.save()

        employee_instance.grade = grade
        employee_instance.specialization = specialization
        employee_instance.type = developer_type

        employee_instance.save()
        return Response(serializer.data)
    print(serializer.errors)
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['GET'])
def employee_get(request):
    employee = Employee.objects.all()
    serializer = EmployeeDetailsSerializer(employee, many=True)
    return Response(serializer.data)

@api_view(['PUT'])
def employee_update(request, employee_id):
    employee = request.data

    developer_type_pkl = open('developer_type_classifier.pkl', 'rb')
    developer_specialization_pkl = open('developer_specialization_classifier.pkl', 'rb')

    dev_type_model = pickle.load(developer_type_pkl)
    dev_specialization_model = pickle.load(developer_specialization_pkl)

    skills = [employee.get('is_html', False), employee.get('is_css', False), employee.get('is_js', False),
              employee.get('is_angular', False), employee.get('is_react', False),
              employee.get('is_bootstrap', False), employee.get('is_java', False), employee.get('is_python', False),
              employee.get('is_ruby', False), employee.get('is_csharp', False), employee.get('is_cplus', False),
              employee.get('is_php', False), employee.get('is_mysql', False), employee.get('is_nosql', False),
              employee.get('is_oracle', False), employee.get('is_leadership', False),
              employee.get('is_teamwork', False), employee.get('is_communication', False),
              employee.get('is_analytical', False), employee.get('is_creativity', False),
              employee.get('is_passion', False)]

    for i in range(0, len(skills)):
        if skills[i]:
            skills[i] = 1
        else:
            skills[i] = 0
        i += 1

    employee_skills_array = array([skills])

    dev_type = dev_type_model.predict(employee_skills_array)
    dev_specialization = dev_specialization_model.predict(employee_skills_array)

    if 'Frontend' in str(dev_type):
        developer_type = 'Frontend'
    elif 'Backend' in str(dev_type):
        developer_type = 'Backend'
    else:
        developer_type = 'Full-Stack'

    if 'Level-1' in str(dev_type):
        grade = 'Level-1'
    elif 'Level-2' in str(dev_type):
        grade = 'Level-2'
    else:
        grade = 'Level-3'

    if 'None' in str(dev_specialization):
        specialization = 'None'
    elif 'Frontend' in str(dev_specialization):
        specialization = 'Frontend'
    elif 'Backend' in str(dev_specialization):
        specialization = 'Backend'
    else:
        specialization = 'Full-Stack'

    employee = Employee.objects.get(id=employee_id)
    serializer = EmployeeDetailsSerializer(employee, data=request.data)
    if serializer.is_valid():
        employee_instance = serializer.save()

        employee_instance.grade = grade
        employee_instance.specialization = specialization
        employee_instance.type = developer_type

        employee_instance.save()
        return Response(serializer.data)
    print(serializer.errors)
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['PUT'])
def employee_assign_project(request, employee_id):
    employee = Employee.objects.get(id=employee_id)
    serializer = EmployeeDetailsSerializer(employee, data=request.data)
    if serializer.is_valid():
        serializer.save()
        return Response(serializer.data)
    print(serializer.errors)
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['DELETE'])
def employee_delete(request, employee_id):
    employee = Employee.objects.get(id=employee_id)
    employee.delete()
    return Response(status=status.HTTP_204_NO_CONTENT)

@api_view(['GET'])
def employee_get_to_update(request, employee_id):
    employee = Employee.objects.filter(id=employee_id)
    if not employee:
        return Response(status=status.HTTP_400_BAD_REQUEST)
    serializer = EmployeeDetailsSerializer(employee, many=True)
    return Response(serializer.data)

@api_view(["GET"])
def employee_search(request, search_query):
    employees = Employee.objects.filter(firstname=search_query)
    if not employees:
        employees = Employee.objects.filter(lastname=search_query)
        if not employees:
            return Response(status=status.HTTP_400_BAD_REQUEST)
        serializer = EmployeeDetailsSerializer(employees, many=True)
        return Response(serializer.data)
    else:
        serializer = EmployeeDetailsSerializer(employees, many=True)
        return Response(serializer.data)

@api_view(['GET'])
def employee_filter(request, filter_query):
    query_type=filter_query[:-8]
    query_level=filter_query[-7:]
    employees = Employee.objects.filter(type=query_type,grade=query_level)
    if not employees:
        return Response(status=status.HTTP_400_BAD_REQUEST)
    serializer = EmployeeDetailsSerializer(employees, many=True)
    return Response(serializer.data)

@api_view(['POST'])
def project_create(request):
    project = request.data
    serializer = ProjectDetailsSerializer(data=project)
    if serializer.is_valid():
        serializer.save()
        return Response(serializer.data)
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['GET'])
def project_get(request):
    project = Project.objects.all()
    serializer = ProjectDetailsSerializer(project, many=True)
    return Response(serializer.data)

@api_view(['PUT'])
def project_update(request, project_id):
    project = Project.objects.get(id=project_id)
    print(project)
    serializer = ProjectDetailsSerializer(project, data=request.data)
    if serializer.is_valid():
        serializer.save()
        return Response(serializer.data)
    print(serializer.errors)
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['DELETE'])
def project_delete(request, project_id):
    project = Project.objects.get(id=project_id)
    project.delete()
    return Response(status=status.HTTP_204_NO_CONTENT)

@api_view(["GET"])
def project_search(request, search_query):
    projects = Project.objects.filter(solution_description__icontains=search_query)
    if not projects:
        projects = Project.objects.filter(client__icontains=search_query)
        if not projects:
            return Response(status=status.HTTP_400_BAD_REQUEST)
        serializer = ProjectDetailsSerializer(projects, many=True)
        return Response(serializer.data)
    else:
        serializer = ProjectDetailsSerializer(projects, many=True)
        return Response(serializer.data)

@api_view(['GET'])
def project_filter(request, filter_query):
    projects = Project.objects.filter(priority=filter_query)
    if not projects:
        return Response(status=status.HTTP_400_BAD_REQUEST)
    serializer = ProjectDetailsSerializer(projects, many=True)
    return Response(serializer.data)

@api_view(['GET'])
def project_get_to_update(request, project_id):
    project = Project.objects.filter(id=project_id)
    if not project:
        return Response(status=status.HTTP_400_BAD_REQUEST)
    serializer = ProjectDetailsSerializer(project, many=True)
    return Response(serializer.data)

@api_view(['PUT'])
def send_email(request):
    email = request.data
    subject = email.get('subject', 'Hello From Inertia')
    message = email.get('message', 'Automated message')
    byWhom = email.get('from', 'victorsihd@gmail.com')
    recipient = [email.get('to', 'sihaddepp@gmail.com')]
    send_mail(subject, message, byWhom, recipient)
    return Response(status=status.HTTP_200_OK)