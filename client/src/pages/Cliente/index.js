import React from 'react';
import {Link} from 'react-router-dom';
import {FiPower, FiEdit, FiTrash2} from 'react-icons/fi';

import './styles.css';
import emporio from '../../assets/emporio.jpg';

export default function Cliente(){
    return(
        <div className="cliente-container">
            <header>
                <img src={emporio} alt="Emporio"/>
                <span><strong>Maxuel</strong></span>
                <span>Pagina de Clientes</span>
                <Link className="button" to="/cliente/novo">Adicionar novo Cliente</Link>
                <button type='button'>
                    <FiPower size={18} color="#251FC5"/>
                </button>
            </header>

            <h1>Registro de Clientes</h1>
            <ul>
                <li>
                    <strong>Nome:</strong>
                    <p>Maxuel</p>
                    <strong>Sobrenome:</strong>
                    <p>Junior</p>
                    <strong>Telefone:</strong>
                    <p>(16)99232-6161</p>
                    <strong>Data de Nascimento</strong>
                    <p>17/01/1999</p>
                    <strong>Endereço</strong>
                    <p>Rua Horacilio Gomes Martins</p>
                    <strong>Sexo</strong>
                    <p>M</p>
                    <strong>Lucratividade</strong>
                    <p>53.0%</p>

                    <button type="button">
                        <FiEdit size={20} color="#251FC5"/>
                    </button>

                    <button type="button">
                        <FiTrash2 size={20} color="#251FC5"/>
                    </button>
                </li>
                <li>
                    <strong>Nome:</strong>
                    <p>Maxuel</p>
                    <strong>Sobrenome:</strong>
                    <p>Junior</p>
                    <strong>Telefone:</strong>
                    <p>(16)99232-6161</p>
                    <strong>Data de Nascimento</strong>
                    <p>17/01/1999</p>
                    <strong>Endereço</strong>
                    <p>Rua Horacilio Gomes Martins</p>
                    <strong>Sexo</strong>
                    <p>M</p>
                    <strong>Lucratividade</strong>
                    <p>53.0%</p>

                    <button type="button">
                        <FiEdit size={20} color="#251FC5"/>
                    </button>

                    <button type="button">
                        <FiTrash2 size={20} color="#251FC5"/>
                    </button>
                </li>
                <li>
                    <strong>Nome:</strong>
                    <p>Maxuel</p>
                    <strong>Sobrenome:</strong>
                    <p>Junior</p>
                    <strong>Telefone:</strong>
                    <p>(16)99232-6161</p>
                    <strong>Data de Nascimento</strong>
                    <p>17/01/1999</p>
                    <strong>Endereço</strong>
                    <p>Rua Horacilio Gomes Martins</p>
                    <strong>Sexo</strong>
                    <p>M</p>
                    <strong>Lucratividade</strong>
                    <p>53.0%</p>

                    <button type="button">
                        <FiEdit size={20} color="#251FC5"/>
                    </button>

                    <button type="button">
                        <FiTrash2 size={20} color="#251FC5"/>
                    </button>
                </li>
                <li>
                    <strong>Nome:</strong>
                    <p>Maxuel</p>
                    <strong>Sobrenome:</strong>
                    <p>Junior</p>
                    <strong>Telefone:</strong>
                    <p>(16)99232-6161</p>
                    <strong>Data de Nascimento</strong>
                    <p>17/01/1999</p>
                    <strong>Endereço</strong>
                    <p>Rua Horacilio Gomes Martins</p>
                    <strong>Sexo</strong>
                    <p>M</p>
                    <strong>Lucratividade</strong>
                    <p>53.0%</p>

                    <button type="button">
                        <FiEdit size={20} color="#251FC5"/>
                    </button>

                    <button type="button">
                        <FiTrash2 size={20} color="#251FC5"/>
                    </button>
                </li>
            </ul>
        </div>
    );
}