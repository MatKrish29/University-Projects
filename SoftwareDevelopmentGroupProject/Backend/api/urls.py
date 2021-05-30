from django.urls import path, include
from rest_framework import routers
from rest_framework.authtoken.views import obtain_auth_token

from api.views import UserViewSet, UserDetail, employee_create, employee_get, employee_update, employee_delete, employee_get_to_update, employee_search, employee_filter,\
    project_create, project_get, project_update, project_delete, project_search, project_filter, project_get_to_update,send_email, \
    employee_assign_project

router = routers.DefaultRouter()
router.register("user", UserViewSet)
# router.register("employee", EmployeeViewSet)
# router.register("project", ProjectViewSet)

urlpatterns = [
    path('', include(router.urls)),
    path('user/<int:id>/', UserDetail.as_view()),

    path('employee/create/', employee_create, name='create-employee'),
    path('employee/get/', employee_get, name='get-employee'),
    path('employee/update/<int:employee_id>/', employee_update, name='update-employee'),
    path('employee/delete/<int:employee_id>/', employee_delete, name='delete-employee'),
    path('employee/get/<int:employee_id>/',employee_get_to_update, name='get-employee-to-update'),
    path('employee/search/<str:search_query>/', employee_search, name='search-employee'),
    path('employee/filter/<str:filter_query>/', employee_filter, name='filter-employee'),
    path('employee/assign/project/<int:employee_id>/', employee_assign_project, name='assign-project-employee'),

    path('project/create/', project_create, name='create-project'),
    path('project/get/', project_get, name='get-project'),
    path('project/update/<int:project_id>/', project_update, name='update-project'),
    path('project/delete/<int:project_id>/', project_delete, name='delete-project'),
    path('project/search/<str:search_query>/', project_search, name='search-project'),
    path('project/filter/<str:filter_query>/', project_filter, name='filter-project'),
    path('project/get/<int:project_id>/', project_get_to_update, name='get-project-to-update'),
    path('auth/', obtain_auth_token),
    path('email/send/', send_email, name='send_email')
]