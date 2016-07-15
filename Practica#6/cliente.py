#!/usr/bin/env python


from suds.client import Client
url = '	http://localhost:9000/Main?wsdl'
client = Client(url)
while True:
	print "a. Creacion de Estudiante"
	print "b. Modificacion de Estudiante"
	print "c. Eliminar Estudiante"
	print "d. Recuperar un estudiante dado su matricula"
	print "e. Listar todos los Estudiantes"
	print "f. Crear Asignaturas"
	print "g. Asignar Asignaturas"
	menu = raw_input()
	if menu=='a':
		print "Nombre:"
		nombre =raw_input()
		print "Matricula:"
		matricula= raw_input()
		print "Carrera"
		carrera = raw_input()
		client.service.crearEstudiante(matricula,nombre,carrera)
	if menu=='b':
		print "Matricula:"
		matricula= raw_input()
		print "Nuevo Nombre:"
		nombre =raw_input()
		print "Nueva Carrera"
		carrera = raw_input()
		client.service.modificarEstudiante(matricula,nombre,carrera)
	if menu=='c':
		print "Matricula:"
		matricula= raw_input()
		client.service.eliminarEstudiante(matricula)	
	if menu=='d':
		print "Matricula:"
		matricula= raw_input()
		estudiante=client.service.getEstudiante(matricula)
		print estudiante
	if menu=='e':
		estudiantes=client.service.getEstudiantes()
		print estudiantes
	if menu=='f':
		print "Nombre:"
		nombre =raw_input()
		print "Codigo:"
		codigo = raw_input()
		client.service.crearAsignatura(codigo,nombre)
	if menu=='g':
		asignaturas=client.service.getAsignaturas()
		print asignaturas
		print "Codigo:"
		codigo = raw_input()
		print "Matricula:"
		matricula= raw_input()
		client.service.asignarAsignatura(codigo,matricula)
