import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier
from sklearn import metrics
import pickle
from sklearn.metrics import confusion_matrix
import numpy as np
from matplotlib import pyplot as plt
import seaborn as sns
from sklearn.metrics import classification_report

from sklearn.model_selection import KFold
from sklearn.model_selection import cross_val_score
from sklearn.linear_model import LogisticRegression
from sklearn.naive_bayes import GaussianNB
from sklearn.neighbors import KNeighborsClassifier
from sklearn.tree import DecisionTreeClassifier
from sklearn.svm import SVC
from sklearn.ensemble import RandomForestClassifier
from sklearn.ensemble import AdaBoostClassifier
from sklearn.ensemble import GradientBoostingClassifier

import seaborn as sns

sns.set()
import warnings
warnings.filterwarnings('ignore')

# Random Forest Classifier

data = pd.read_csv("Dataset.csv")

data.drop(['Employee-ID'], axis=1, inplace=True)
data.drop(['FirstName'], axis=1, inplace=True)
data.drop(['LastName'], axis=1, inplace=True)
data.drop(['Job-Profile'], axis=1, inplace=True)

print(data.columns)

# 0 - Beginner
# 1 - Frontend
# 2 - Backend
# 3 - Full-Stack

# Labelling frontend developer
data.loc[((data['HTML'] == 1) & (data['CSS'] == 1) & (data['JAVASCRIPT'] == 1))
         # | (data['ANGULAR'] == 1) | (data['REACTJS'] == 1) | (data['BOOTSTRAP'] == 1))
         & ((data['JAVA'] == 0) & (data['C#'] == 0) & (data['PYTHON'] == 0) & (data['PHP'] == 0) & (data['C++'] == 0)
         & (data['RUBY'] == 0)),
         'DEVELOPER TYPE'] = 1

# Labelling backend developer
data.loc[((data['HTML'] == 0) & (data['CSS'] == 0) & (data['JAVASCRIPT'] == 0)) & ((data['JAVA'] == 1)
         | (data['C#'] == 1) | (data['PYTHON'] == 1) | (data['PHP'] == 1)
         | (data['C++'] == 1) | (data['RUBY'] == 1)),
         'DEVELOPER TYPE'] = 2

# Labelling full-stack developer
data.loc[(data['DEVELOPER TYPE'] != 1) & (data['DEVELOPER TYPE'] != 2),
         'DEVELOPER TYPE'] = 3

# Labelling level-3 frontend developer
data.loc[(data['DEVELOPER TYPE'] == 1) & (((data['ANGULAR'] == 1) & (data['BOOTSTRAP'] == 1) & (data['REACTJS'] == 1))
         & (data['CREATIVE'] == 1)) & (data['HTML'] == 1)
         & (data['CSS'] == 1) & (data['JAVASCRIPT'] == 1)
         & (((data['COMMUNICATION'] == 1) | (data['LEADERSHIP'] == 1)) & (data['PASSION'] == 1) & (data['TEAMWORK'] == 1)),
         'GRADE'] = 'Frontend(Level-3)'

# Labelling level-2 frontend developer
data.loc[(data['DEVELOPER TYPE'] == 1) & (data['GRADE'] != 'Frontend(Level-3)')
         & (((((data['ANGULAR'] == 1) & (data['BOOTSTRAP'] == 0))
         | ((data['REACTJS'] == 1) & (data['BOOTSTRAP'] == 0)))
         & (data['CREATIVE'] == 1))
         & ((data['COMMUNICATION'] == 1) | (data['TEAMWORK'] == 1 | (data['LEADERSHIP'] == 1))
         & (data['PASSION'] == 1))

         |((((data['ANGULAR'] == 1) & (data['BOOTSTRAP'] == 0))
         & ((data['REACTJS'] == 1) & (data['BOOTSTRAP'] == 0)))
         & (data['CREATIVE'] == 0))
         & ((data['COMMUNICATION'] == 1) & (data['TEAMWORK'] == 1 & (data['LEADERSHIP'] == 1))
         & (data['PASSION'] == 1))

         |((((data['ANGULAR'] == 1) & (data['BOOTSTRAP'] == 0))
         & ((data['REACTJS'] == 1) & (data['BOOTSTRAP'] == 0)))
         & (data['CREATIVE'] == 1))
         & ((data['COMMUNICATION'] == 1) & (data['TEAMWORK'] == 1 & (data['LEADERSHIP'] == 1))
         & (data['PASSION'] == 0))

         | ((((data['ANGULAR'] == 1) & (data['BOOTSTRAP'] == 1))
         & ((data['REACTJS'] == 1) & (data['BOOTSTRAP'] == 1)))
         & (data['CREATIVE'] == 0))
         & ((data['COMMUNICATION'] == 1) | (data['TEAMWORK'] == 1 | (data['LEADERSHIP'] == 1))
         & (data['PASSION'] == 1))

         | ((((data['ANGULAR'] == 1) & (data['BOOTSTRAP'] == 1))
         & ((data['REACTJS'] == 1) & (data['BOOTSTRAP'] == 1)))
         & (data['CREATIVE'] == 1))
         & ((data['COMMUNICATION'] == 1) | (data['TEAMWORK'] == 1 | (data['LEADERSHIP'] == 1))
         & (data['PASSION'] == 0)))

         , 'GRADE'] = 'Frontend(Level-2)'

# Labelling level-1 frontend developer
data.loc[(data['DEVELOPER TYPE'] == 1) & ((data['GRADE'] != 'Frontend(Level-3)')
         & (data['GRADE'] != 'Frontend(Level-2)')),
         'GRADE'] = 'Frontend(Level-1)'

# data.reset_index(drop=True, inplace=True)
# print(data.MYSQL)

# Labelling level-3 backend developer
data.loc[(data['DEVELOPER TYPE'] == 2) & ((data['JAVA'] == 1) & (data['PYTHON'] == 1) & (data['PHP'] == 1)
         & (data['RUBY'] == 1)) & ((data['C#'] == 1) | (data['C++'] == 1)) & (data['ANALYTICAL'] == 1)
         & ((data['MYSQL'] == 1) | (data['ORACLE'] == 1) | (data['NOSQL/PostgreSQL'] == 1))
         & (data['PASSION'] == 1) & (data['TEAMWORK'] == 1) & ((data['COMMUNICATION'] == 1)
         | (data['LEADERSHIP'] == 1)),
         'GRADE'] = 'Backend(Level-3)'

# Labelling level-2 backend developer
data.loc[(data['DEVELOPER TYPE'] == 2) & (data['GRADE'] != 'Backend(Level-3)') & (((data['JAVA'] == 1)
         | (data['PYTHON'] == 1) | (data['C#'] == 1) | (data['C++'] == 1)
         | (data['PHP'] == 1) | (data['RUBY'] == 1) & (data['ANALYTICAL'] == 1))
         & ((data['MYSQL'] == 1) | (data['NOSQL/PostgreSQL'] == 1) | (data['ORACLE'] == 1))
         & ((data['PASSION'] == 1) | ((data['TEAMWORK'] == 1) | (data['COMMUNICATION'] == 1)
         | (data['LEADERSHIP'] == 1)))),
         'GRADE'] = 'Backend(Level-2)'

# Labelling level-1 backend developer
data.loc[(data['DEVELOPER TYPE'] == 2) & ((data['GRADE'] != 'Backend(Level-3)')
         & (data['GRADE'] != 'Backend(Level-2)')),
         'GRADE'] = 'Backend(Level-1)'

# Labelling level-3 full-stack developer
data.loc[(data['DEVELOPER TYPE'] == 3) & (((((data['ANGULAR'] == 1) & (data['BOOTSTRAP'] == 1) & (data['REACTJS'] == 1)))
                                          & (data['CREATIVE'] == 1)) & ((data['HTML'] == 1)
                                          & (data['CSS'] == 1) & (data['JAVASCRIPT'] == 1)) & (((data['JAVA'] == 1)
                                          & (data['PYTHON'] == 1) & (data['PHP'] == 1) & (data['RUBY'] == 1))
                                          & ((data['C#'] == 1) | (data['C++'] == 1)) & (data['ANALYTICAL'] == 1))
                                          & ((data['MYSQL'] == 1) | (data['NOSQL/PostgreSQL'] == 1)
                                          | (data['ORACLE'] == 1))
                                          & (((data['PASSION'] == 1) & (data['TEAMWORK'] == 1)))
                                          & ((data['COMMUNICATION'] == 1) | (data['LEADERSHIP'] == 1))),
         'GRADE'] = 'Full-Stack(Level-3)'

# Labelling level-2 full-stack developer
data.loc[(data['DEVELOPER TYPE'] == 3) & (data['GRADE'] != 'Full-Stack(Level-3)')
         & (((((data['ANGULAR'] == 1) & (data['BOOTSTRAP'] == 0))
         | ((data['REACTJS'] == 1) & (data['BOOTSTRAP'] == 0)))
         & (data['CREATIVE'] == 1))

         | ((((data['ANGULAR'] == 1) & (data['BOOTSTRAP'] == 1))
         & ((data['REACTJS'] == 1) & (data['BOOTSTRAP'] == 1)))
         & (data['CREATIVE'] == 0))

         | ((((data['ANGULAR'] == 1) & (data['BOOTSTRAP'] == 1))
         & ((data['REACTJS'] == 1) & (data['BOOTSTRAP'] == 1)))
         & (data['CREATIVE'] == 1)))

         & ((((data['JAVA'] == 1) | (data['PYTHON'] == 1) | (data['C#'] == 1) | (data['C++'] == 1)
         | (data['PHP'] == 1) | (data['RUBY'] == 1) & (data['ANALYTICAL'] == 1))
         & ((data['MYSQL'] == 1) | (data['NOSQL/PostgreSQL'] == 1) | (data['ORACLE'] == 1)))

         & ((data['PASSION'] == 1) | ((data['TEAMWORK'] == 1) | (data['COMMUNICATION'] == 1)
         | (data['LEADERSHIP'] == 1))))
         , 'GRADE'] = 'Full-Stack(Level-2)'

# Labelling level-1 full-stack developer
data.loc[(data['DEVELOPER TYPE'] == 3) & ((data['GRADE'] != 'Full-Stack(Level-3)')
                                          & (data['GRADE'] != 'Full-Stack(Level-2)')),
         'GRADE'] = 'Full-Stack(Level-1)'

# Frontend specialization
data.loc[((data['GRADE'] == 'Full-Stack(Level-1)') | (data['GRADE'] == 'Full-Stack(Level-2)')
         | (data['GRADE'] == 'Frontend(Level-3)')) & (((data['ANGULAR'] == 1) & (data['BOOTSTRAP'] == 1) & (data['REACTJS'] == 1))
         & (data['CREATIVE'] == 1)) & (data['HTML'] == 1)
         & (data['CSS'] == 1) & (data['JAVASCRIPT'] == 1)
         & (((data['COMMUNICATION'] == 1) | (data['LEADERSHIP'] == 1)) & (data['PASSION'] == 1) & (data['TEAMWORK'] == 1)),
         'SPECIALIZATION'] = 'Frontend'

# Backend specialization
data.loc[((data['GRADE'] == 'Full-Stack(Level-1)') | (data['GRADE'] == 'Full-Stack(Level-2)')
         | (data['GRADE'] == 'Backend(Level-3)')) & ((data['JAVA'] == 1) & (data['PYTHON'] == 1) & (data['PHP'] == 1)
         & (data['RUBY'] == 1)) & ((data['C#'] == 1) | (data['C++'] == 1)) & (data['ANALYTICAL'] == 1)
         & ((data['MYSQL'] == 1) | (data['ORACLE'] == 1) | (data['NOSQL/PostgreSQL'] == 1))
         & (data['PASSION'] == 1) & (data['TEAMWORK'] == 1) & ((data['COMMUNICATION'] == 1)
         | (data['LEADERSHIP'] == 1)),
         'SPECIALIZATION'] = 'Backend'

# Full-Stack specialization
data.loc[(data['GRADE'] == 'Full-Stack(Level-3)') & ((((((data['ANGULAR'] == 1) & (data['BOOTSTRAP'] == 1) & (data['REACTJS'] == 1))))
                                          & (data['CREATIVE'] == 1)) & (data['HTML'] == 1)
                                          & (data['CSS'] == 1) & (data['JAVASCRIPT'] == 1) & (((data['JAVA'] == 1)
                                          & (data['PYTHON'] == 1) & (data['PHP'] == 1) & (data['RUBY'] == 1))
                                          & ((data['C#'] == 1) | (data['C++'] == 1)) & (data['ANALYTICAL'] == 1))
                                          & ((data['MYSQL'] == 1) | (data['NOSQL/PostgreSQL'] == 1)
                                          | (data['ORACLE'] == 1))
                                          & ((data['COMMUNICATION'] == 1) & (data['TEAMWORK'] == 1))
                                          & (data['PASSION'] == 1) | (data['LEADERSHIP'] == 1)),
         'SPECIALIZATION'] = 'Full-Stack'

data.loc[(data['SPECIALIZATION'] != 'Frontend') & (data['SPECIALIZATION'] != 'Backend')
         & (data['SPECIALIZATION'] != 'Full-Stack'), 'SPECIALIZATION'] = 'None'

# Defining independent variables
X = data[['HTML', 'CSS', 'JAVASCRIPT', 'ANGULAR', 'REACTJS', 'BOOTSTRAP', 'JAVA', 'PYTHON', 'RUBY',  'C#', 'C++', 'PHP',
        'MYSQL', 'NOSQL/PostgreSQL', 'ORACLE', 'LEADERSHIP', 'TEAMWORK', 'COMMUNICATION', 'ANALYTICAL', 'CREATIVE'
    , 'PASSION']]

# Defining dependent variable
Y = data['GRADE']

# Splitting dataset into training set and test set
X_train, X_test, Y_train, Y_test = train_test_split(X, Y, test_size=0.3)

# Creating a classifier
classifier = RandomForestClassifier(n_estimators=100)

# Training the model on the training set
classifier.fit(X_train, Y_train)

# Performing predictions on the test set
Y_predictor = classifier.predict(X_test)
# trainSet = classifier.predict(X_train)

X2 = data[['HTML', 'CSS', 'JAVASCRIPT', 'ANGULAR', 'REACTJS', 'BOOTSTRAP', 'JAVA', 'PYTHON', 'RUBY',  'C#', 'C++', 'PHP'
    , 'MYSQL', 'NOSQL/PostgreSQL', 'ORACLE', 'LEADERSHIP', 'TEAMWORK', 'COMMUNICATION', 'ANALYTICAL', 'CREATIVE',
           'PASSION']]
Y2 = data['SPECIALIZATION']

X2_train, X2_test, Y2_train, Y2_test = train_test_split(X2, Y2, test_size=0.3)

classifier2 = RandomForestClassifier(n_estimators=100)

classifier2.fit(X2_train, Y2_train)

Y2_predictor = classifier2.predict(X2_test)

# Printing first 50 rows of dataset
print(data.head(50))
data.to_csv('LabelledDataset.csv')

# print('Accuracy: ', metrics.accuracy_score(Y_train, trainSet))
# Checking the accuracy of the test set
print('Accuracy: ', metrics.accuracy_score(Y_test, Y_predictor))
print(confusion_matrix(Y_test, Y_predictor))

# Finding the important features
attributeList = list(X.columns)
importantAttributes = pd.Series(classifier.feature_importances_, index=attributeList).sort_values(ascending=False)
print(importantAttributes)

print('Accuracy: ', metrics.accuracy_score(Y2_test, Y2_predictor))
print(confusion_matrix(Y2_test, Y2_predictor))

attributeList2 = list(X2.columns)
importantAttributes2 = pd.Series(classifier2.feature_importances_, index=attributeList2).sort_values(ascending=False)
print(importantAttributes2)

html1 = 1
css1 = 1
js1 = 1
angular1 = 1
react1 = 1
bootstrap1 = 1
java1 = 1
python1 = 1
ruby1 = 1
cs1 = 1
cpp1 = 1
php1 = 1
mysql1 = 1
nosql1 = 1
oracle1 = 1
leadership1 = 1
teamwork1 = 1
communication1 = 1
analytical1 = 1
creative1 = 1
passion1 = 1

html0 = 0
css0 = 0
js0 = 0
angular0 = 0
react0 = 0
bootstrap0 = 0
java0 = 0
python0 = 0
ruby0 = 0
cs0 = 0
cpp0 = 0
php0 = 0
mysql0 = 0
nosql0 = 0
oracle0 = 0
leadership0 = 0
teamwork0 = 0
communication0 = 0
analytical0 = 0
creative0 = 0
passion0 = 0

# Making predictions for a new data
# devType = classifier.predict([[1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0]])
# devSpecial = classifier2.predict([[1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0]])
devType = classifier.predict([[html1, css1, js1, angular1, react1, bootstrap1, java1, python1, ruby1, cs0, cpp0, php1,
                               mysql1, nosql0, oracle0, leadership0, teamwork1, communication1, analytical1, creative1,
                               passion1]])
devSpecial = classifier2.predict([[html1, css1, js1, angular1, react1, bootstrap1, java1, python1, ruby1, cs0, cpp0, php1,
                               mysql1, nosql0, oracle0, leadership0, teamwork1, communication1, analytical1, creative1,
                               passion1]])

print('Developer Potential: ', devType)
print('Specialization: ', devSpecial)

# Get and reshape confusion matrix data
matrix = confusion_matrix(Y2_test, Y2_predictor)
matrix = matrix.astype('float') / matrix.sum(axis=1)[:, np.newaxis]

# Build the plot
plt.figure(figsize=(16, 7))
sns.set(font_scale=1.4)
sns.heatmap(matrix, annot=True, annot_kws={'size':10},
            cmap=plt.cm.Greens, linewidths=0.2)

# Add labels to the plot
class_names = ['Frontend(Level-1)', 'Frontend(Level-2)', 'Frontend(Level-3)', 'Backend(Level-1)', 'Backend(Level-2)',
               'Backend(Level-3)', 'Full-Stack(Level-1)', 'Full-Stack(Level-2)', 'Full-Stack(Level-3)']
tick_marks = np.arange(len(class_names))
tick_marks2 = tick_marks + 0.5
plt.xticks(tick_marks, class_names, rotation=25)
plt.yticks(tick_marks2, class_names, rotation=0)
plt.xlabel('Predicted label')
plt.ylabel('True label')
plt.title('Confusion Matrix for Random Forest Model')
plt.show()

print(classification_report(Y_test, Y_predictor))
print(classification_report(Y2_test, Y2_predictor))

dev_type_pkl = open('developer_type_classifier.pkl', 'wb')
dev_specialization_pkl = open('developer_specialization_classifier.pkl', 'wb')

pickle.dump(classifier, dev_type_pkl)
pickle.dump(classifier2, dev_specialization_pkl)

models = []

models.append(("Logistic Regression:", LogisticRegression()))
models.append(("Naive Bayes:", GaussianNB()))
models.append(("K-Nearest Neighbour:", KNeighborsClassifier(n_neighbors=3)))
models.append(("Decision Tree:", DecisionTreeClassifier()))
models.append(("Support Vector Machine-linear:", SVC(kernel="linear")))
models.append(("Support Vector Machine-rbf:", SVC(kernel="rbf")))
models.append(("Random Forest:", RandomForestClassifier(n_estimators=7)))
models.append(("AdaBoostClassifier:", AdaBoostClassifier()))
models.append(("GradientBoostingClassifier:", GradientBoostingClassifier()))

print('Models appended...')

results = []
names = []
for name, model in models:
    kfold = KFold(n_splits=10, random_state=0)
    cv_result = cross_val_score(model, X_train, Y_train.values.ravel(), cv=kfold, scoring="accuracy")
    names.append(name)
    results.append(cv_result)
for i in range(len(names)):
    print(names[i], results[i].mean() * 100)










