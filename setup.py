from setuptools import setup, find_packages

with open("requirements.txt") as f:
	install_requires = f.read().strip().split("\n")

# get version from __version__ variable in rebalance/__init__.py
from rebalance import __version__ as version

setup(
	name="rebalance",
	version=version,
	description="reBalance life",
	author="Gavin D\'souza",
	author_email="gavin18d@gmail.com",
	packages=find_packages(),
	zip_safe=False,
	include_package_data=True,
	install_requires=install_requires
)
