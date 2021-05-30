import random

from sklearn.cluster import KMeans

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

import joblib

df = pd.read_csv('LabelledDataset.csv')

df.drop(['DEVELOPER TYPE'], axis=1, inplace=True)


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
df_html_size = df['GRADE'].size
size_range = range(1, df_html_size)
for i in size_range:
    if df.at[i - 1, "GRADE"] == 0:
        df.loc[i - 1:i, "GRADE"] = random.uniform(1.00, 50.00)

    if df.at[i - 1, "GRADE"] == 1:
        df.loc[i - 1:i, "GRADE"] = random.uniform(200.00, 250.00)

    if df.at[i - 1, "GRADE"] == 2:
        df.loc[i - 1:i, "GRADE"] = random.uniform(400.00, 450.00)

    if df.at[i - 1, "GRADE"] == 3:
        df.loc[i - 1:i, "GRADE"] = random.uniform(600.00, 650.00)

    if df.at[i - 1, "GRADE"] == 4:
        df.loc[i - 1:i, "GRADE"] = random.uniform(800.00, 850.00)

    if df.at[i - 1, "GRADE"] == 5:
        df.loc[i - 1:i, "GRADE"] = random.uniform(1000.00, 1050.00)

    if df.at[i - 1, "GRADE"] == 6:
        df.loc[i - 1:i, "GRADE"] = random.uniform(1200.00, 1250.00)

    if df.at[i - 1, "GRADE"] == 7:
        df.loc[i - 1:i, "GRADE"] = random.uniform(1400.00, 1450.00)

    if df.at[i - 1, "GRADE"] == 8:
        df.loc[i - 1:i, "GRADE"] = random.uniform(1600.00, 1650.00)

for i in size_range:
    if 0 <= df.at[i - 1, "Experience(Yrs)"] <= 5:
        df.loc[i - 1:i, "Experience(Yrs)"] = random.uniform(1.00, 50.00)

    if 5 < df.at[i - 1, "Experience(Yrs)"] < 11:
        df.loc[i - 1:i, "Experience(Yrs)"] = random.uniform(150.00, 200.00)

    if 11 < df.at[i - 1, "Experience(Yrs)"]:
        df.loc[i - 1:i, "Experience(Yrs)"] = random.uniform(300.00, 350.00)

sum_of_squared_distance = []
K = range(1, 20)
for k in K:
    km = KMeans(n_clusters=k)
    km = km.fit(df[['Experience(Yrs)', 'GRADE']])
    sum_of_squared_distance.append(km.inertia_)

plt.plot(K, sum_of_squared_distance, 'bx-')
plt.xlabel('k')
plt.ylabel('sum_of_squared_distance')
plt.title('Elbow method for optimal k')

df = (df[['Experience(Yrs)', 'GRADE']] - df[['Experience(Yrs)', 'GRADE']].mean()) / df[
    ['Experience(Yrs)', 'GRADE']].std()

Kmean = KMeans(n_clusters=9, init="k-means++")
Kmean.fit(df[['Experience(Yrs)', 'GRADE']])
print(Kmean.cluster_centers_)
df['CLUSTER'] = Kmean.labels_

# print(df.head())
# print(df['CLUSTER'].value_counts())

df.to_csv("ClusteredData", index=False)

clu_pred = Kmean.predict([[4, 8]])
print(clu_pred)
plt.show()

plt.scatter(df['Experience(Yrs)'], df['GRADE'])
plt.scatter(-1.31453884, 1.24235575, c="r", marker="x")
plt.scatter(0.15807746, -0.77596478, c="g", marker="x")
plt.scatter(1.27700746, 1.18795515, c="c", marker="x")

plt.scatter(-1.36715, -0.77456298, c="m", marker="s")
plt.scatter(1.43557507, -0.77127776, c="y", marker="s")
plt.scatter(-0.50142739, 1.12842379, c="k", marker="s")

plt.scatter(-0.63596971, -0.76038308, c="b", marker="s")
plt.scatter(0.39823543, 1.27379587, c="m", marker="s")
plt.scatter(0.83879928, -0.76539164, c="y", marker="s")

plt.xlabel("Experience(Yrs)")
plt.ylabel("GRADE")
plt.show()

file_name = "clustered_model.sav"
joblib.dump(Kmean, file_name)

# clu_pred = joblib.load(file_name)
# cluster = clu_pred.predict([[4, 8]])
# print(cluster)