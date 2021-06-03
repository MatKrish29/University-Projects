import random

from sklearn.cluster import KMeans

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

pd.set_option("display.max_rows", None, "display.max_columns", None)

df = pd.read_csv('LabelledDataset.csv')

df.drop(['Employee-ID'], axis=1, inplace=True)
df.drop(['DEVELOPER TYPE'], axis=1, inplace=True)
df.drop(['SPECIALIZATION'], axis=1, inplace=True)
df.drop(['Experience(Yrs)'], axis=1, inplace=True)


# convert non numeric data into numeric data
def handle_non_numerical_data(df):
    columns = df.columns.values
    for column in columns:
        # if (column == 'First Name' or column == 'Last Name'):
        # continue
        text_digit_vals = {}

        def convert_to_int(val):
            return text_digit_vals[val]

        if df[column].dtype != np.int64 and df[column].dtype != np.float64:
            column_contents = df[column].values.tolist()
            unique_elements = set(column_contents)
            x = 0
            for unique in unique_elements:
                if unique not in text_digit_vals:
                    text_digit_vals[unique] = x
                    x += 1
            df[column] = list(map(convert_to_int, df[column]))
    return df


df = handle_non_numerical_data(df)

df_html_size = df['HTML'].size
size_range = range(1, df_html_size)
for i in size_range:
    df.loc[i - 1:i, "HTML"] = random.uniform(1.00, 20.00)
    df.loc[i - 1:i, "CSS"] = random.uniform(40.00, 60.00)
    df.loc[i - 1:i, "JAVASCRIPT"] = random.uniform(80.00, 100.00)
    df.loc[i - 1:i, "JAVA"] = random.uniform(120.00, 140.00)
    df.loc[i - 1:i, "PYTHON"] = random.uniform(160.00, 180.00)
    df.loc[i - 1:i, "ANGULAR"] = random.uniform(200.00, 220.00)
    df.loc[i - 1:i, "REACTJS"] = random.uniform(240.00, 260.00)
    df.loc[i - 1:i, "C++"] = random.uniform(280.00, 300.00)
    df.loc[i - 1:i, "PHP"] = random.uniform(320.00, 340.00)
    df.loc[i - 1:i, "MYSQL"] = random.uniform(360.00, 380.00)
    df.loc[i - 1:i, "MACHINE LEARNING"] = random.uniform(400.00, 420.00)
    df.loc[i - 1:i, "C#"] = random.uniform(440.00, 460.00)
    df.loc[i - 1:i, "BOOTSTRAP"] = random.uniform(480.00, 500.00)
    df.loc[i - 1:i, "NOSQL/PostgreSQL"] = random.uniform(520.00, 540.00)
    df.loc[i - 1:i, "RUBY"] = random.uniform(560.00, 580.00)
    df.loc[i - 1:i, "NODE.JS"] = random.uniform(600.00, 620.00)
    df.loc[i - 1:i, "ORACLE"] = random.uniform(640.00, 660.00)
    df.loc[i - 1:i, "CREATIVE"] = random.uniform(680.00, 700.00)
    df.loc[i - 1:i, "LEADERSHIP"] = random.uniform(720.00, 740.00)
    df.loc[i - 1:i, "PASSION"] = random.uniform(760.00, 780.00)
    df.loc[i - 1:i, "TEAMWORK"] = random.uniform(800.00, 820.00)
    df.loc[i - 1:i, "COMMUNICATION"] = random.uniform(840.00, 860.00)
    df.loc[i - 1:i, "ANALYTICAL"] = random.uniform(880.00, 900.00)

print(df.head(5))
print(df['HTML'].head(5))

sum_of_squared_distance = []
K = range(1, 10)
for k in K:
    km = KMeans(n_clusters=k)
    km = km.fit(df[['GRADE']])
    sum_of_squared_distance.append(km.inertia_)

plt.plot(K, sum_of_squared_distance, 'bx-')
plt.xlabel('k')
plt.ylabel('sum_of_squared_distance')
plt.title('Elbow method for optimal k')
plt.show()

from sklearn.preprocessing import MinMaxScaler

# scaler = MinMaxScaler()
# scaler.fit(df)
# MinMaxScaler()
# df = scaler.transform(df)

Kmean = KMeans(n_clusters=9, init="k-means++")
Kmean.fit(df[['GRADE']])

Kmean.cluster_centers_
df['CLUSTER'] = Kmean.labels_
print(df.head())
print(df['CLUSTER'].value_counts())

df.to_csv("ClusteredData", index=False)

clu_pred = Kmean.predict([[8]])
print(clu_pred)
plt.scatter(df['GRADE'], df['CLUSTER'])
plt.xlabel("GRADE")
plt.ylabel("CLUSTER")
plt.show()

label = Kmean.fit_predict(df[['GRADE']])
print(label)

# filter rows of original data
filtered_label2 = df[label == 2]

filtered_label8 = df[label == 4]
print(filtered_label8)
